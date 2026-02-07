package com.project.kanban;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(
        name = "api_test", // 👈 tên bạn muốn hiển thị
        description = "API xử lý test"
)
public class TestController {

        @RequestMapping
        public String test() {
            return "Kanban Application is running!";
        }
}
