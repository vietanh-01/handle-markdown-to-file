package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MarkdownServiceTest {

    private MarkdownService markdownService;

    @BeforeEach
    public void setUp() {
        markdownService = new MarkdownService();
    }

    @Test
    public void testComplexMarkdownConversion() throws IOException {
        String complexMarkdown = "# Tài liệu hướng dẫn hoàn chỉnh\n\n" +
                "## 1. Giới thiệu\n\n" +
                "Đây là một tài liệu **hướng dẫn** với nhiều *định dạng* khác nhau. " +
                "Bao gồm ***bold và italic*** cùng lúc.\n\n" +
                "### 1.1. Mục đích\n\n" +
                "Kiểm tra tất cả các trường hợp có thể xảy ra với Markdown:\n\n" +
                "- **Headings** (H1-H6)\n" +
                "- **Paragraphs** với inline formatting\n" +
                "- **Tables** phức tạp\n" +
                "- **Lists** (bullet và numbered)\n" +
                "  - Nested lists level 1\n" +
                "    - Nested lists level 2\n" +
                "- **Code blocks**\n" +
                "- **Blockquotes**\n" +
                "- **Links** và inline `code`\n\n" +
                "## 2. Bảng dữ liệu\n\n" +
                "| STT | Tên | Mô tả | Trạng thái |\n" +
                "|-----|-----|-------|------------|\n" +
                "| 1 | **Feature A** | Tính năng quan trọng | ✅ Hoàn thành |\n" +
                "| 2 | Feature B | Đang phát triển | 🚧 In Progress |\n" +
                "| 3 | Feature C | Kế hoạch tương lai | 📅 Planned |\n\n" +
                "## 3. Code Examples\n\n" +
                "Đây là inline code: `System.out.println(\"Hello World\");`\n\n" +
                "Và đây là code block:\n\n" +
                "```java\n" +
                "public class Example {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Complex Code\");\n" +
                "    }\n" +
                "}\n" +
                "```\n\n" +
                "## 4. Blockquote\n\n" +
                "> Đây là một blockquote quan trọng.\n" +
                "> Có thể có nhiều dòng.\n" +
                "> \n" +
                "> Và cả các đoạn riêng biệt.\n\n" +
                "## 5. Danh sách có số\n\n" +
                "1. Item đầu tiên\n" +
                "2. Item thứ hai\n" +
                "   1. Nested item 2.1\n" +
                "   2. Nested item 2.2\n" +
                "3. Item thứ ba\n\n" +
                "## 6. Links và Formatting\n\n" +
                "Đây là một [link](https://example.com) trong paragraph. " +
                "Kết hợp với **bold text** và *italic text*.\n\n" +
                "---\n\n" +
                "## 7. Kết luận\n\n" +
                "Tài liệu này kiểm tra **tất cả** các *trường hợp* có thể xảy ra.";

        byte[] result = markdownService.convertMarkdownToWord(complexMarkdown);

        assertNotNull(result);
        assertTrue(result.length > 0);

        // Save to file for manual inspection
        try (FileOutputStream fos = new FileOutputStream("target/complex_test.docx")) {
            fos.write(result);
        }
    }

    @Test
    public void testVietnameseContent() throws IOException {
        String vietnameseMarkdown = "# Tiếng Việt có dấu\n\n" +
                "## Các ký tự đặc biệt\n\n" +
                "| Chữ cái | Ví dụ |\n" +
                "|---------|-------|\n" +
                "| À Á Ả Ã Ạ | Âm thanh |\n" +
                "| Ă Ằ Ắ Ẳ Ẵ Ặ | Ăn uống |\n" +
                "| Ê Ề Ế Ể Ễ Ệ | Bê bối |\n" +
                "| Ô Ồ Ố Ổ Ỗ Ộ | Ồn ào |\n" +
                "| Ơ Ờ Ớ Ở Ỡ Ợ | Ơi là ơi |\n" +
                "| Ư Ừ Ứ Ử Ữ Ự | Ưu tiên |\n\n" +
                "### Câu văn hoàn chỉnh\n\n" +
                "Việt Nam là một **quốc gia** có *nền văn hóa* lâu đời. " +
                "Tiếng Việt sử dụng ***29 chữ cái*** trong bảng chữ cái.";

        byte[] result = markdownService.convertMarkdownToWord(vietnameseMarkdown);

        assertNotNull(result);
        assertTrue(result.length > 0);

        // Save to file
        try (FileOutputStream fos = new FileOutputStream("target/vietnamese_test.docx")) {
            fos.write(result);
        }
    }

    @Test
    public void testEdgeCases() throws IOException {
        String edgeCases = "# Edge Cases Test\n\n" +
                "## Empty elements\n\n" +
                "| Empty | Table |\n" +
                "|-------|-------|\n" +
                "| | |\n\n" +
                "## Multiple consecutive formatting\n\n" +
                "**Bold** *Italic* ***BoldItalic*** `code` **Bold again**\n\n" +
                "## Long table\n\n" +
                "| Col1 | Col2 | Col3 | Col4 | Col5 |\n" +
                "|------|------|------|------|------|\n" +
                "| Data1 | Data2 | Data3 | Data4 | Data5 |\n" +
                "| Very long data that might wrap | Short | Medium length | X | YYYY |\n\n" +
                "## Deep nesting\n\n" +
                "- Level 1\n" +
                "  - Level 2\n" +
                "    - Level 3\n" +
                "      - Level 4\n\n" +
                "1. Numbered 1\n" +
                "   1. Numbered 1.1\n" +
                "      1. Numbered 1.1.1\n\n" +
                "## Special characters\n\n" +
                "Characters: & < > \" ' @ # $ % ^ * ( ) - _ = + [ ] { } | \\ / ? . , ; :\n\n" +
                "## Mixed lists\n\n" +
                "1. First numbered\n" +
                "   - Bullet inside numbered\n" +
                "   - Another bullet\n" +
                "2. Second numbered\n" +
                "   1. Nested numbered\n" +
                "   2. Another nested";

        byte[] result = markdownService.convertMarkdownToWord(edgeCases);

        assertNotNull(result);
        assertTrue(result.length > 0);

        // Save to file
        try (FileOutputStream fos = new FileOutputStream("target/edge_cases_test.docx")) {
            fos.write(result);
        }
    }

    @Test
    public void testAllHeadingLevels() throws IOException {
        String headings = "# Heading 1\n\n" +
                "## Heading 2\n\n" +
                "### Heading 3\n\n" +
                "#### Heading 4\n\n" +
                "##### Heading 5\n\n" +
                "###### Heading 6\n\n" +
                "Regular paragraph text.";

        byte[] result = markdownService.convertMarkdownToWord(headings);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    public void testEmptyMarkdown() throws IOException {
        String empty = "";
        byte[] result = markdownService.convertMarkdownToWord(empty);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    public void testOnlyText() throws IOException {
        String text = "Just plain text without any markdown formatting.";
        byte[] result = markdownService.convertMarkdownToWord(text);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }
}
