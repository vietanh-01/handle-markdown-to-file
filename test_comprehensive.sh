#!/bin/bash

# Comprehensive Markdown to Word Conversion Test Script

echo "Testing Markdown to Word Conversion API..."
echo "=========================================="
echo ""

# Test 1: Vietnamese Military Document (Original example)
echo "Test 1: Vietnamese Military Document..."
curl -X POST http://localhost:8080/api/doc/download \
  -H "Content-Type: application/json" \
  -d '{
    "markdown":"Chào anh/chị Thiếu tá,\n\nDưới đây là tóm tắt các quy định quan trọng về Giấy chứng minh sĩ quan (GCS) hiện hành theo Thông tư 65/2025/TT-BQP (cùng các Thông tư, Nghị định, Luật liên quan). Nếu cần chi tiết hơn về mẫu tờ khai, quy trình tại đơn vị cụ thể, hoặc các trường hợp ngoại lệ, anh/chị cứ cho mình biết nhé.\n\n## Cơ sở pháp lý chính\n\n| Văn bản | Nội dung liên quan |\n|---------|--------------------|\n| Luật Sĩ quan Quân đội Nhân dân Việt Nam (1999, sửa 2008, 2014) | Định nghĩa, quyền lợi, nghĩa vụ của sĩ quan; quy định về GCS. |\n| Nghị định 130/2008/NĐ-CP | Quy định chi tiết về Giấy chứng minh sĩ quan (số hiệu, nội dung, thời hạn, hình thức bảo quản). |\n| Thông tư 218/2016/TT-BQP | Hướng dẫn thi hành NĐ 130/2008, quy trình cấp, đổi, thu hồi GCS. |\n| Thông tư 65/2025/TT-BQP (hiệu lực 03/07/2025) | Sửa đổi, bổ sung một số điều của Thông tư 218/2016, bao gồm: <br/>- Thời hạn sử dụng <br/>- Quy trình cấp lại, đổi mới <br/>- Yêu cầu về dữ liệu sinh trắc học và mã QR. |\n| Nghị định 59/2016/NĐ-CP | Quy định về chứng minh nhân dân chuyên nghiệp, công nhân, viên chức quốc phòng. |"
  }' \
  --output test1_military_doc.docx
echo "✅ Created: test1_military_doc.docx"
echo ""

# Test 2: Technical Documentation with Code
echo "Test 2: Technical Documentation..."
curl -X POST http://localhost:8080/api/doc/download \
  -H "Content-Type: application/json" \
  -d '{
    "markdown":"# Hướng dẫn lập trình Java\n\n## 1. Giới thiệu\n\nJava là một **ngôn ngữ lập trình** hướng đối tượng được phát triển bởi *Sun Microsystems*.\n\n### 1.1. Đặc điểm\n\n- ***Write Once, Run Anywhere*** (WORA)\n- Quản lý bộ nhớ tự động\n- Đa nền tảng\n- Bảo mật cao\n\n## 2. Cú pháp cơ bản\n\n### 2.1. Hello World\n\n```java\npublic class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}\n```\n\n### 2.2. Biến và kiểu dữ liệu\n\n| Kiểu | Kích thước | Ví dụ |\n|------|------------|-------|\n| `int` | 32-bit | `int age = 25;` |\n| `double` | 64-bit | `double pi = 3.14;` |\n| `String` | Variable | `String name = \"Java\";` |\n| `boolean` | 1-bit | `boolean isValid = true;` |\n\n## 3. Vòng lặp\n\n### 3.1. For Loop\n\n```java\nfor (int i = 0; i < 10; i++) {\n    System.out.println(\"Count: \" + i);\n}\n```\n\n### 3.2. While Loop\n\n```java\nint count = 0;\nwhile (count < 5) {\n    System.out.println(count);\n    count++;\n}\n```\n\n> **Lưu ý**: Luôn kiểm tra điều kiện thoát để tránh vòng lặp vô tận.\n\n---\n\n## 4. Best Practices\n\n1. **Đặt tên có ý nghĩa**\n   - Class: `PascalCase`\n   - Method: `camelCase`\n   - Constant: `UPPER_SNAKE_CASE`\n\n2. **Xử lý exceptions**\n   - Sử dụng `try-catch` blocks\n   - Đóng resources trong `finally`\n\n3. **Code documentation**\n   - Viết JavaDoc cho public methods\n   - Comment giải thích logic phức tạp\n\n## Kết luận\n\nJava là một ngôn ngữ mạnh mẽ và linh hoạt, phù hợp cho cả người mới và chuyên gia."
  }' \
  --output test2_technical_doc.docx
echo "✅ Created: test2_technical_doc.docx"
echo ""

# Test 3: Complex Mixed Content
echo "Test 3: Complex Mixed Content..."
curl -X POST http://localhost:8080/api/doc/download \
  -H "Content-Type: application/json" \
  -d '{
    "markdown":"# Báo cáo dự án Q4/2025\n\n## Executive Summary\n\nDự án đã hoàn thành **95%** các mục tiêu đề ra với *hiệu suất vượt trội*.\n\n### Các chỉ số chính\n\n| Chỉ số | Mục tiêu | Thực tế | Chênh lệch |\n|--------|----------|---------|------------|\n| Doanh thu | 100M VNĐ | 125M VNĐ | +25% |\n| Khách hàng mới | 500 | 650 | +30% |\n| Satisfaction | 85% | 92% | +7% |\n| Bug rate | < 5% | 3.2% | -1.8% |\n\n## Tiến độ theo giai đoạn\n\n### Phase 1: Planning (Hoàn thành ✅)\n\n1. **Phân tích yêu cầu**\n   - Khảo sát thị trường\n   - Xác định user personas\n   - Lập roadmap\n\n2. **Thiết kế hệ thống**\n   - Database schema\n   - API design\n   - UI/UX mockups\n\n### Phase 2: Development (Hoàn thành ✅)\n\n#### Backend Development\n\n```java\n@RestController\n@RequestMapping(\"/api\")\npublic class UserController {\n    @PostMapping(\"/users\")\n    public ResponseEntity<User> createUser(@RequestBody UserDTO dto) {\n        // Implementation\n        return ResponseEntity.ok(user);\n    }\n}\n```\n\n#### Frontend Development\n\n- ✅ React components\n- ✅ State management (Redux)\n- ✅ Responsive design\n- ✅ Unit tests (95% coverage)\n\n### Phase 3: Testing (In Progress 🚧)\n\n- [ ] Integration testing\n- [x] Unit testing\n- [x] Performance testing\n- [ ] Security audit\n\n> **Quan trọng**: Phase 3 cần hoàn thành trước ngày 31/12/2025\n\n---\n\n## Rủi ro và vấn đề\n\n### Rủi ro đã xác định\n\n1. **Technical Debt**\n   - Legacy code cần refactor\n   - Thiếu documentation\n   - Solution: Dành 20% thời gian mỗi sprint\n\n2. **Resource Constraints**\n   - Team size nhỏ\n   - Budget hạn chế\n   - Solution: Outsource non-core tasks\n\n### Vấn đề đã giải quyết\n\n| Vấn đề | Severity | Giải pháp | Status |\n|--------|----------|-----------|--------|\n| Performance bottleneck | High | Database optimization | ✅ Resolved |\n| Security vulnerability | Critical | Patch deployed | ✅ Resolved |\n| UI inconsistency | Medium | Design system | ✅ Resolved |\n\n## Kế hoạch tiếp theo\n\n### Q1/2026 Roadmap\n\n1. **Product Enhancement**\n   - Mobile app launch\n   - AI-powered features\n   - Multi-language support\n\n2. **Infrastructure**\n   - Cloud migration\n   - CI/CD pipeline\n   - Monitoring & alerting\n\n3. **Team Growth**\n   - Tuyển 5 developers\n   - Training program\n   - Knowledge sharing sessions\n\n### Metrics to Track\n\n```javascript\nconst metrics = {\n  revenue: { target: 150000000, unit: \"VND\" },\n  users: { target: 1000, growth: \"monthly\" },\n  performance: { responseTime: \"< 200ms\" },\n  uptime: { target: \"99.9%\" }\n};\n```\n\n---\n\n## Kết luận\n\n**Thành công lớn** trong Q4/2025 với sự nỗ lực của toàn team. Tiếp tục phát huy trong năm 2026!\n\n### Liên hệ\n\n- Project Manager: [Nguyễn Văn A](mailto:nguyenvana@example.com)\n- Tech Lead: [Trần Thị B](mailto:tranthib@example.com)\n- Product Owner: [Lê Văn C](mailto:levanc@example.com)"
  }' \
  --output test3_complex_report.docx
echo "✅ Created: test3_complex_report.docx"
echo ""

echo "=========================================="
echo "All tests completed! Files created:"
echo "- test1_military_doc.docx"
echo "- test2_technical_doc.docx"
echo "- test3_complex_report.docx"
echo ""
echo "Open these files to verify the conversion quality."

