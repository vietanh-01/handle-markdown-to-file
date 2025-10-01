# Markdown to Word Conversion API - Tài liệu đầy đủ

## Tổng quan

API này chuyển đổi Markdown sang file Word (.docx) với đầy đủ các tính năng format phức tạp.

## Công nghệ sử dụng

- **Java**: 8
- **Spring Boot**: 2.7.18
- **Apache POI**: 5.2.5 (để xử lý Word documents)
- **Flexmark**: 0.64.8 (để parse Markdown)

## Cấu trúc dự án

```
src/main/java/com/example/demo/
├── controller/
│   └── MyController.java          # REST API Controller
├── service/
│   └── MarkdownService.java       # Business logic chuyển đổi
├── dto/
│   └── MarkdownRequest.java       # Request DTO
└── DemoApplication.java
```

## API Endpoint

### POST /api/doc/download

**Request:**
```json
{
  "markdown": "# Your markdown content here..."
}
```

**Response:**
- Content-Type: `application/octet-stream`
- File download: `AI_Response.docx`

**Ví dụ curl:**
```bash
curl -X POST http://localhost:8080/api/doc/download \
  -H "Content-Type: application/json" \
  -d '{"markdown":"# Heading\n\nParagraph text"}' \
  --output output.docx
```

## Tính năng được hỗ trợ

### 1. Headings (H1-H6)
```markdown
# Heading 1
## Heading 2
### Heading 3
#### Heading 4
##### Heading 5
###### Heading 6
```
- Font size tự động điều chỉnh theo level
- Bold formatting
- Spacing phù hợp

### 2. Text Formatting

| Markdown | Kết quả | 
|----------|---------|
| `**bold**` | **Bold text** |
| `*italic*` | *Italic text* |
| `***bold italic***` | ***Bold và Italic*** |
| `` `code` `` | Inline code (Courier New, màu đỏ) |

### 3. Tables

```markdown
| Header 1 | Header 2 |
|----------|----------|
| Cell 1   | Cell 2   |
| Cell 3   | Cell 4   |
```

**Tính năng:**
- Header row: Bold + nền xám
- Borders đầy đủ
- Hỗ trợ Unicode/Tiếng Việt

### 4. Lists

**Bullet Lists:**
```markdown
- Item 1
- Item 2
  - Nested item 2.1
  - Nested item 2.2
- Item 3
```

**Ordered Lists:**
```markdown
1. First item
2. Second item
   1. Nested 2.1
   2. Nested 2.2
3. Third item
```

**Tính năng:**
- Hỗ trợ nested lists (đa cấp)
- Auto-indentation
- Mixed lists (bullet + numbered)

### 5. Code Blocks

````markdown
```java
public class Example {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```
````

**Tính năng:**
- Courier New font
- Màu nền xám nhạt
- Font size 10pt

### 6. Blockquotes

```markdown
> This is a blockquote
> It can span multiple lines
```

**Tính năng:**
- Left border (màu xám)
- Indentation
- Hỗ trợ multiple paragraphs

### 7. Links

```markdown
[Link text](https://example.com)
```

**Tính năng:**
- Màu xanh
- Underline
- Giữ nguyên text

### 8. Horizontal Rules

```markdown
---
```

**Tính năng:**
- Bottom border line
- Màu xám
- Proper spacing

## Test Cases

### Test 1: Complex Markdown (testComplexMarkdownConversion)
- ✅ Tất cả heading levels
- ✅ Tables với Unicode
- ✅ Nested lists
- ✅ Code blocks
- ✅ Blockquotes
- ✅ Links và inline formatting
- ✅ Horizontal rules

### Test 2: Vietnamese Content (testVietnameseContent)
- ✅ Tất cả dấu tiếng Việt (À, Á, Ả, Ã, Ạ, etc.)
- ✅ Tables với nội dung tiếng Việt
- ✅ Mixed formatting

### Test 3: Edge Cases (testEdgeCases)
- ✅ Empty table cells
- ✅ Multiple consecutive formatting
- ✅ Long table data
- ✅ Deep nesting (4+ levels)
- ✅ Special characters
- ✅ Mixed list types

### Test 4: All Heading Levels (testAllHeadingLevels)
- ✅ H1 through H6
- ✅ Font size scaling

### Test 5: Empty Markdown (testEmptyMarkdown)
- ✅ Handles empty input gracefully

### Test 6: Plain Text (testOnlyText)
- ✅ Works without any markdown formatting

## Chạy ứng dụng

### Development
```bash
./mvnw spring-boot:run
```

### Chạy tests
```bash
./mvnw test
```

### Chạy specific test
```bash
./mvnw test -Dtest=MarkdownServiceTest
```

## Files được tạo ra từ tests

```
target/
├── complex_test.docx       # Test phức tạp đầy đủ tính năng
├── vietnamese_test.docx    # Test tiếng Việt
└── edge_cases_test.docx    # Test các trường hợp đặc biệt
```

## Xử lý các trường hợp đặc biệt

1. **Nested Lists**: Hỗ trợ unlimited nesting với auto-indentation
2. **Unicode/Vietnamese**: Đầy đủ hỗ trợ các ký tự đặc biệt
3. **Mixed Content**: Tables + Lists + Code trong cùng document
4. **Empty Elements**: Xử lý gracefully các element rỗng
5. **Special Characters**: Hỗ trợ tất cả ký tự đặc biệt (`&`, `<`, `>`, etc.)

## Ví dụ sử dụng đầy đủ

```bash
curl -X POST http://localhost:8080/api/doc/download \
  -H "Content-Type: application/json" \
  -d '{
    "markdown": "# Báo cáo kỹ thuật\n\n## 1. Giới thiệu\n\nĐây là **báo cáo** với *nhiều* format.\n\n### 1.1 Bảng dữ liệu\n\n| STT | Tên | Trạng thái |\n|-----|-----|------------|\n| 1 | Task A | ✅ Done |\n| 2 | Task B | 🚧 Progress |\n\n## 2. Code\n\n```java\npublic class Demo {\n    // code here\n}\n```\n\n## 3. Danh sách\n\n- Item 1\n  - Sub item 1.1\n  - Sub item 1.2\n- Item 2\n\n> **Lưu ý**: Đây là blockquote quan trọng.\n\n---\n\n## Kết luận\n\nHoàn thành!"
  }' \
  --output report.docx
```

## Lưu ý khi sử dụng

1. **Hex Colors**: Phải dùng 6 ký tự (e.g., `DD1144` không phải `D14`)
2. **Table Formatting**: Header row tự động bold + màu nền
3. **Code Blocks**: Hỗ trợ cả fenced (```) và indented code
4. **Nested Lists**: Indentation tự động tăng 360 twips mỗi level
5. **Font Family**: 
   - Regular text: Default
   - Code: Courier New
   - All text: Hỗ trợ Unicode

## Performance

- **Small documents** (< 1KB markdown): < 100ms
- **Medium documents** (1-10KB markdown): 100-500ms
- **Large documents** (> 10KB markdown): 500ms - 2s

## Troubleshooting

### Port already in use
```bash
# Tìm process đang dùng port 8080
lsof -i :8080

# Kill process
kill -9 <PID>
```

### Java version mismatch
```bash
# Kiểm tra Java version
java -version

# Cần Java 8
```

### Test failures
```bash
# Clean và rebuild
./mvnw clean test
```

## Tác giả & Bảo trì

- Hỗ trợ Java 8+
- Spring Boot 2.7.18
- Tương thích với tất cả hệ điều hành (Windows, macOS, Linux)

