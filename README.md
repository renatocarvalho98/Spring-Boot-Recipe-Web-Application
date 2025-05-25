Developed a full-stack web application using Spring Boot, Thymeleaf, and MongoDB, designed for users to search, save, and manage recipes from an external API. The project evolved from a simple CRUD into a dynamic, authenticated platform with a clean, responsive UI and REST integrations.

Key Features Implemented:

🔐 User Authentication & Authorization:

Secure registration and login system using Spring Security and BCrypt encryption.

Session management and access control for protected routes.

🔎 Recipe Search & API Integration:

Integrated with Spoonacular API to allow users to search real recipes.

Implemented recipe search with real-time query and result rendering using Thymeleaf.

💾 Save & Delete Recipes:

Authenticated users can save favorite recipes to a personal collection stored in MongoDB.

Implemented delete functionality for saved recipes.

👤 User Profile Management:

Profile page with edit and delete account functionality.

Password change and profile updates handled with Spring MVC.

🎨 UI/UX & Styling:

Designed a clean, responsive UI using custom CSS (login.css, register.css, main.css).

Created a dynamic header fragment with navigation that adapts based on authentication state.

Used Thymeleaf layout fragments and expressions for dynamic content rendering.

🛠️ Technology Stack:

Backend: Java, Spring Boot (MVC, Security, Data), MongoDB

Frontend: Thymeleaf, HTML/CSS (custom styling)

Tools: IntelliJ, Maven, Postman (API tests)
