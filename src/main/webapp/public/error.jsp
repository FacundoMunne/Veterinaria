<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - Veterinaria</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
    <style>
        .error-header {
            background-color: ${errorColor};
            color: white;
            padding: 1rem;
            border-radius: 5px;
        }
        pre {
            white-space: pre-wrap;
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <main class="container">
        <div class="error-header">
            <h1>${errorType}</h1>
        </div>
        
        <article>
            <p>${errorMessage}</p>
            
            <!-- Solo mostrar detalles en desarrollo -->
            <c:if test="${initParam.modoDesarrollo == 'true'}">
                <details>
                    <summary>Detalles técnicos</summary>
                    <pre><c:out value="${errorDebug}"/></pre>
                </details>
            </c:if>
            
            <a href="${errorRedirect}" class="button">${errorButtonText}</a>
        </article>
    </main>
</body>
</html>