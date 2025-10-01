package com.example.demo.service;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MarkdownService {

    private final Parser parser;

    public MarkdownService() {
        // Configure parser to support tables
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Collections.singletonList(TablesExtension.create()));
        this.parser = Parser.builder(options).build();
    }

    public byte[] convertMarkdownToWord(String markdown) throws IOException {
        // Parse Markdown
        Node document = parser.parse(markdown);

        // Create Word document
        XWPFDocument doc = new XWPFDocument();

        // Process markdown nodes
        processNode(doc, document, null);

        // Export as byte[]
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out);
        doc.close();

        return out.toByteArray();
    }

    private void processNode(XWPFDocument doc, Node node, XWPFParagraph currentParagraph) {
        for (Node child : node.getChildren()) {
            if (child instanceof Heading) {
                createHeading(doc, (Heading) child);
            } else if (child instanceof Paragraph) {
                createParagraph(doc, (Paragraph) child);
            } else if (child instanceof TableBlock) {
                createTable(doc, (TableBlock) child);
            } else if (child instanceof BulletList) {
                createBulletList(doc, (BulletList) child, 0);
            } else if (child instanceof OrderedList) {
                createOrderedList(doc, (OrderedList) child, 0);
            } else if (child instanceof BlockQuote) {
                createBlockQuote(doc, (BlockQuote) child);
            } else if (child instanceof FencedCodeBlock || child instanceof IndentedCodeBlock) {
                createCodeBlock(doc, child);
            } else if (child instanceof ThematicBreak) {
                createHorizontalRule(doc);
            } else {
                processNode(doc, child, currentParagraph);
            }
        }
    }

    private void createHeading(XWPFDocument doc, Heading heading) {
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();

        String text = heading.getText().toString();
        run.setText(text);
        run.setBold(true);

        // Set heading size based on level
        int level = heading.getLevel();
        int fontSize = 18 - (level * 2); // H1=16, H2=14, H3=12, etc.
        run.setFontSize(Math.max(fontSize, 11));

        paragraph.setSpacingAfter(100);
    }

    private void createParagraph(XWPFDocument doc, Paragraph para) {
        XWPFParagraph paragraph = doc.createParagraph();
        processInlineContent(paragraph, para);
        paragraph.setSpacingAfter(100);
    }

    private void processInlineContent(XWPFParagraph paragraph, Node node) {
        for (Node child : node.getChildren()) {
            if (child instanceof Text) {
                XWPFRun run = paragraph.createRun();
                run.setText(((Text) child).getChars().toString());
            } else if (child instanceof StrongEmphasis) {
                XWPFRun run = paragraph.createRun();
                String text = getTextContent(child);
                run.setText(text);
                run.setBold(true);
            } else if (child instanceof Emphasis) {
                XWPFRun run = paragraph.createRun();
                String text = getTextContent(child);
                run.setText(text);
                run.setItalic(true);
            } else if (child instanceof Code) {
                XWPFRun run = paragraph.createRun();
                run.setText(((Code) child).getText().toString());
                run.setFontFamily("Courier New");
                run.setColor("DD1144");
            } else if (child instanceof SoftLineBreak || child instanceof HardLineBreak) {
                XWPFRun run = paragraph.createRun();
                run.addBreak();
            } else if (child instanceof Link) {
                Link link = (Link) child;
                XWPFRun run = paragraph.createRun();
                String linkText = getTextContent(link);
                run.setText(linkText);
                run.setColor("0000FF");
                run.setUnderline(UnderlinePatterns.SINGLE);
            } else {
                processInlineContent(paragraph, child);
            }
        }
    }

    private String getTextContent(Node node) {
        StringBuilder text = new StringBuilder();
        for (Node child : node.getChildren()) {
            if (child instanceof Text) {
                text.append(((Text) child).getChars());
            } else {
                text.append(getTextContent(child));
            }
        }
        return text.toString();
    }

    private void createTable(XWPFDocument doc, TableBlock tableBlock) {
        List<List<String>> tableData = new ArrayList<>();

        // Extract table data
        for (Node child : tableBlock.getChildren()) {
            if (child instanceof TableHead) {
                for (Node row : child.getChildren()) {
                    if (row instanceof TableRow) {
                        tableData.add(extractTableRow((TableRow) row));
                    }
                }
            } else if (child instanceof TableBody) {
                for (Node row : child.getChildren()) {
                    if (row instanceof TableRow) {
                        tableData.add(extractTableRow((TableRow) row));
                    }
                }
            }
        }

        if (tableData.isEmpty())
            return;

        // Create table
        int cols = tableData.get(0).size();
        XWPFTable table = doc.createTable(tableData.size(), cols);

        // Set table borders
        table.setWidth("100%");

        // Fill table data
        for (int i = 0; i < tableData.size(); i++) {
            XWPFTableRow row = table.getRow(i);
            List<String> rowData = tableData.get(i);

            for (int j = 0; j < rowData.size(); j++) {
                XWPFTableCell cell = row.getCell(j);
                cell.setText(rowData.get(j));

                // Bold header row
                if (i == 0) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            r.setBold(true);
                        }
                    }
                    cell.setColor("E0E0E0"); // Light gray background for header
                }

                // Add borders
                CTTcPr tcPr = cell.getCTTc().addNewTcPr();
                CTTcBorders borders = tcPr.addNewTcBorders();
                addBorder(borders.addNewTop());
                addBorder(borders.addNewBottom());
                addBorder(borders.addNewLeft());
                addBorder(borders.addNewRight());
            }
        }

        // Add spacing after table
        doc.createParagraph().setSpacingAfter(100);
    }

    private void addBorder(CTBorder border) {
        border.setVal(STBorder.SINGLE);
        border.setSz(BigInteger.valueOf(4));
        border.setColor("000000");
    }

    private List<String> extractTableRow(TableRow tableRow) {
        List<String> rowData = new ArrayList<>();
        for (Node cell : tableRow.getChildren()) {
            if (cell instanceof TableCell) {
                StringBuilder cellText = new StringBuilder();
                extractText(cell, cellText);
                rowData.add(cellText.toString().trim());
            }
        }
        return rowData;
    }

    private void extractText(Node node, StringBuilder text) {
        if (node instanceof Text) {
            text.append(((Text) node).getChars());
        }
        for (Node child : node.getChildren()) {
            extractText(child, text);
        }
    }

    private void createBulletList(XWPFDocument doc, BulletList list, int depth) {
        for (Node item : list.getChildren()) {
            if (item instanceof BulletListItem) {
                XWPFParagraph paragraph = doc.createParagraph();
                paragraph.setIndentationLeft(720 + (depth * 360)); // 0.5 inch + nested indent
                XWPFRun run = paragraph.createRun();
                run.setText("• ");

                // Process inline content and nested lists
                for (Node child : item.getChildren()) {
                    if (child instanceof BulletList) {
                        createBulletList(doc, (BulletList) child, depth + 1);
                    } else if (child instanceof OrderedList) {
                        createOrderedList(doc, (OrderedList) child, depth + 1);
                    } else if (child instanceof Paragraph) {
                        processInlineContent(paragraph, child);
                    }
                }
            }
        }
    }

    private void createOrderedList(XWPFDocument doc, OrderedList list, int depth) {
        int counter = 1;
        for (Node item : list.getChildren()) {
            if (item instanceof OrderedListItem) {
                XWPFParagraph paragraph = doc.createParagraph();
                paragraph.setIndentationLeft(720 + (depth * 360)); // 0.5 inch + nested indent
                XWPFRun run = paragraph.createRun();
                run.setText(counter + ". ");

                // Process inline content and nested lists
                for (Node child : item.getChildren()) {
                    if (child instanceof BulletList) {
                        createBulletList(doc, (BulletList) child, depth + 1);
                    } else if (child instanceof OrderedList) {
                        createOrderedList(doc, (OrderedList) child, depth + 1);
                    } else if (child instanceof Paragraph) {
                        processInlineContent(paragraph, child);
                    }
                }
                counter++;
            }
        }
    }

    private void createBlockQuote(XWPFDocument doc, BlockQuote blockQuote) {
        for (Node child : blockQuote.getChildren()) {
            if (child instanceof Paragraph) {
                XWPFParagraph paragraph = doc.createParagraph();
                paragraph.setIndentationLeft(720);

                // Add left border for blockquote
                if (paragraph.getCTP().getPPr() == null) {
                    paragraph.getCTP().addNewPPr();
                }
                CTBorder border = paragraph.getCTP().getPPr().addNewPBdr().addNewLeft();
                border.setVal(STBorder.SINGLE);
                border.setSz(BigInteger.valueOf(8));
                border.setColor("CCCCCC");

                processInlineContent(paragraph, child);
                paragraph.setSpacingAfter(100);
            }
        }
    }

    private void createCodeBlock(XWPFDocument doc, Node codeBlock) {
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setStyle("CodeBlock");

        String code;
        if (codeBlock instanceof FencedCodeBlock) {
            code = ((FencedCodeBlock) codeBlock).getContentChars().toString();
        } else {
            code = ((IndentedCodeBlock) codeBlock).getContentChars().toString();
        }

        XWPFRun run = paragraph.createRun();
        run.setText(code);
        run.setFontFamily("Courier New");
        run.setFontSize(10);

        // Add background color
        CTShd shd = run.getCTR().addNewRPr().addNewShd();
        shd.setFill("F6F8FA");

        paragraph.setSpacingAfter(100);
    }

    private void createHorizontalRule(XWPFDocument doc) {
        XWPFParagraph paragraph = doc.createParagraph();

        // Add bottom border for horizontal rule
        if (paragraph.getCTP().getPPr() == null) {
            paragraph.getCTP().addNewPPr();
        }
        CTBorder border = paragraph.getCTP().getPPr().addNewPBdr().addNewBottom();
        border.setVal(STBorder.SINGLE);
        border.setSz(BigInteger.valueOf(6));
        border.setColor("CCCCCC");

        paragraph.setSpacingAfter(100);
    }
}
