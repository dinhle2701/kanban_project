package com.project.kanban.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;

import java.util.*;

@Configuration
public class SwaggerConfig {

    // ✅ Bean này để hiển thị nút "Authorize"
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Kanban Project API")
                        .version("1.0.0")
                        .description("Kanban API documentation with JWT authentication - design by Dinh Le"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    // ✅ Bean cũ của bạn (giữ nguyên để sắp xếp API)
    @Bean
    public GlobalOpenApiCustomizer globalSorter() {
        return (OpenAPI openApi) -> {
            openApi.getPaths().forEach((path, pathItem) -> reorderOperations(path, pathItem));

            List<Map.Entry<String, PathItem>> entries = new ArrayList<>(openApi.getPaths().entrySet());
            entries.sort((a, b) -> comparePaths(a.getKey(), b.getKey()));

            Paths sortedPaths = new Paths();
            for (Map.Entry<String, PathItem> e : entries) {
                sortedPaths.addPathItem(e.getKey(), e.getValue());
            }
            openApi.setPaths(sortedPaths);
        };
    }

    // ---------- Helpers (giữ nguyên) ----------
    private void reorderOperations(String path, PathItem pathItem) {
        Map<PathItem.HttpMethod, Operation> original = pathItem.readOperationsMap();
        LinkedHashMap<PathItem.HttpMethod, Operation> ordered = new LinkedHashMap<>();

        boolean byId = isIdPath(path);

        if (!byId && original.containsKey(PathItem.HttpMethod.GET)) {
            ordered.put(PathItem.HttpMethod.GET, original.get(PathItem.HttpMethod.GET));
        }

        if (byId && original.containsKey(PathItem.HttpMethod.GET)) {
            ordered.put(PathItem.HttpMethod.GET, original.get(PathItem.HttpMethod.GET));
        }

        if (original.containsKey(PathItem.HttpMethod.POST)) {
            ordered.put(PathItem.HttpMethod.POST, original.get(PathItem.HttpMethod.POST));
        }
        if (original.containsKey(PathItem.HttpMethod.PUT)) {
            ordered.put(PathItem.HttpMethod.PUT, original.get(PathItem.HttpMethod.PUT));
        }
        if (original.containsKey(PathItem.HttpMethod.PATCH)) {
            ordered.put(PathItem.HttpMethod.PATCH, original.get(PathItem.HttpMethod.PATCH));
        }
        if (original.containsKey(PathItem.HttpMethod.DELETE)) {
            ordered.put(PathItem.HttpMethod.DELETE, original.get(PathItem.HttpMethod.DELETE));
        }

        original.forEach(ordered::putIfAbsent);
        ordered.forEach(pathItem::operation);
    }

    private int comparePaths(String p1, String p2) {
        String base1 = baseOf(p1);
        String base2 = baseOf(p2);
        int cmpBase = base1.compareTo(base2);
        if (cmpBase != 0) {
            return cmpBase;
        }

        boolean id1 = isIdPath(p1);
        boolean id2 = isIdPath(p2);
        if (id1 == id2) {
            return p1.compareTo(p2);
        }
        return id1 ? 1 : -1;
    }

    private String baseOf(String path) {
        if (path == null) return "";
        int i = path.indexOf("/{");
        return i >= 0 ? path.substring(0, i) : path;
    }

    private boolean isIdPath(String path) {
        return path != null && path.contains("{") && path.contains("}");
    }
}
