-- Truncate tables (in reverse order of dependencies)
TRUNCATE TABLE view_history;
TRUNCATE TABLE likes;
TRUNCATE TABLE comments;
TRUNCATE TABLE posts;
TRUNCATE TABLE users;

-- Insert sample users
INSERT INTO users (username, name, password, email, contact, bio, created_at) VALUES
("johndoe", "John Doe", "password123", "john@example.com", "555-1234", "Software developer with 5 years experience", NOW()),
("janedoe", "Jane Doe", "password456", "jane@example.com", "555-5678", "UX designer passionate about user experiences", NOW()),
("bobsmith", "Bob Smith", "pass789", "bob@example.com", "555-9012", "Full stack developer and open source contributor", NOW()),
("alicejones", "Alice Jones", "securepass", "alice@example.com", "555-3456", "Data scientist with expertise in machine learning", NOW()),
("mikebrown", "Mike Brown", "mikepass", "mike@example.com", "555-7890", "DevOps engineer specializing in cloud infrastructure", NOW()),
("sarahlee", "Sarah Lee", "sarahsecure", "sarah@example.com", "555-2345", "Frontend developer with a passion for React", NOW()),
("davidwilson", "David Wilson", "davidspass", "david@example.com", "555-6789", "Mobile app developer with iOS and Android experience", NOW()),
("emilyclark", "Emily Clark", "emilypass", "emily@example.com", "555-0123", "Backend developer specializing in Java and Spring", NOW()),
("chrismartin", "Chris Martin", "chrispass", "chris@example.com", "555-4567", "QA engineer with automation expertise", NOW()),
("laurawang", "Laura Wang", "laurapass", "laura@example.com", "555-8901", "System architect with 10+ years experience", NOW()),
("ryanking", "Ryan King", "ryanpass", "ryan@example.com", "555-2345", "Security specialist and ethical hacker", NOW()),
("oliviabrown", "Olivia Brown", "oliviapass", "olivia@example.com", "555-6789", "Database administrator and SQL expert", NOW()),
("jameswright", "James Wright", "jamespass", "james@example.com", "555-0123", "Technical writer and documentation specialist", NOW()),
("sophiakim", "Sophia Kim", "sophiapass", "sophia@example.com", "555-4567", "UI designer with expertise in Figma", NOW()),
("danieljones", "Daniel Jones", "danielpass", "daniel@example.com", "555-8901", "Product manager with technical background", NOW());

-- Insert sample posts
INSERT INTO posts (title, content, author_id, created_at, updated_at, category, likes, views) VALUES
("Introduction to Spring Boot", "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications...", 1, NOW(), NOW(), "Java", 0, 0),
("Mastering React Hooks", "React Hooks are a powerful way to handle state and side effects in functional components...", 2, NOW(), NOW(), "JavaScript", 0, 0),
("Docker for Beginners", "Docker containers wrap up software and its dependencies into a standardized unit...", 3, NOW(), NOW(), "DevOps", 0, 0),
("Python Data Analysis with Pandas", "Pandas is a powerful data manipulation library for Python...", 4, NOW(), NOW(), "Python", 0, 0),
("Microservices Architecture", "Microservices architecture is an approach to building applications as a collection of small services...", 5, NOW(), NOW(), "Architecture", 0, 0),
("Advanced CSS Techniques", "Learn how to create stunning visual effects with pure CSS...", 6, NOW(), NOW(), "CSS", 0, 0),
("Machine Learning Basics", "An introduction to the fundamental concepts of machine learning...", 7, NOW(), NOW(), "AI", 0, 0),
("Securing RESTful APIs", "Best practices for securing your RESTful APIs with JWT and OAuth2...", 8, NOW(), NOW(), "Security", 0, 0),
("GraphQL vs REST", "A comparison of GraphQL and REST approaches to building APIs...", 9, NOW(), NOW(), "API", 0, 0),
("CI/CD Pipeline Implementation", "Step-by-step guide to setting up a CI/CD pipeline with Jenkins...", 10, NOW(), NOW(), "DevOps", 0, 0),
("Reactive Programming with RxJS", "Understanding reactive programming paradigms with RxJS...", 11, NOW(), NOW(), "JavaScript", 0, 0),
("Database Optimization Techniques", "Improve your database performance with these optimization techniques...", 12, NOW(), NOW(), "Database", 0, 0),
("Kubernetes for Scalability", "How to use Kubernetes to scale your applications effectively...", 13, NOW(), NOW(), "DevOps", 0, 0),
("Functional Programming in Java", "Exploring functional programming concepts in Java with lambdas and streams...", 14, NOW(), NOW(), "Java", 0, 0),
("Building RESTful APIs with Spring Boot", "A comprehensive guide to building RESTful APIs using Spring Boot...", 15, NOW(), NOW(), "Java", 0, 0);

-- Insert sample comments
INSERT INTO comments (user_id, post_id, content, created_at) VALUES
(2, 1, "Great introduction! Would love to see a follow-up on Spring Security.", NOW()),
(3, 1, "This helped me get started with Spring Boot. Thanks!", NOW()),
(1, 2, "React Hooks changed how I build components. Great article!", NOW()),
(4, 3, "Docker has been a game-changer for my development workflow.", NOW()),
(5, 4, "Pandas is so powerful. I use it daily for data analysis.", NOW()),
(6, 5, "Great overview of microservices architecture.", NOW()),
(7, 6, "These CSS techniques are amazing! I've applied several already.", NOW()),
(8, 7, "Machine learning doesn't seem so intimidating after reading this.", NOW()),
(9, 8, "Security is often overlooked. Great to see content on this topic.", NOW()),
(10, 9, "I've been debating whether to use GraphQL or stick with REST. This helped!", NOW()),
(11, 10, "Implementing CI/CD has saved my team so much time.", NOW()),
(12, 11, "RxJS is complex but powerful. This article explains it well.", NOW()),
(13, 12, "These optimization techniques improved my query performance by 50%!", NOW()),
(14, 13, "Kubernetes has been essential for our scaling strategy.", NOW()),
(15, 14, "I'm new to functional programming in Java. This was very insightful.", NOW());

-- Insert sample likes
INSERT INTO likes (user_id, post_id, clicked_at) VALUES
(1, 2, NOW()),
(1, 3, NOW()),
(2, 1, NOW()),
(2, 3, NOW()),
(3, 1, NOW()),
(3, 2, NOW()),
(4, 5, NOW()),
(5, 4, NOW()),
(6, 7, NOW()),
(7, 6, NOW()),
(8, 9, NOW()),
(9, 8, NOW()),
(10, 11, NOW()),
(11, 10, NOW()),
(12, 13, NOW());

-- Insert sample view history
INSERT INTO view_history (user_id, post_id, clicked_at) VALUES
(1, 2, NOW()),
(1, 3, NOW()),
(1, 4, NOW()),
(2, 1, NOW()),
(2, 3, NOW()),
(3, 1, NOW()),
(3, 2, NOW()),
(4, 5, NOW()),
(5, 4, NOW()),
(6, 7, NOW()),
(7, 6, NOW()),
(8, 9, NOW()),
(9, 8, NOW()),
(10, 11, NOW()),
(11, 10, NOW());

-- Update posts to reflect likes and views
UPDATE posts SET likes = 2 WHERE id = 1;
UPDATE posts SET likes = 2 WHERE id = 2;
UPDATE posts SET likes = 2 WHERE id = 3;
UPDATE posts SET likes = 1 WHERE id = 4;
UPDATE posts SET likes = 1 WHERE id = 5;
UPDATE posts SET likes = 1 WHERE id = 6;
UPDATE posts SET likes = 1 WHERE id = 7;
UPDATE posts SET likes = 1 WHERE id = 8;
UPDATE posts SET likes = 1 WHERE id = 9;
UPDATE posts SET likes = 1 WHERE id = 10;
UPDATE posts SET likes = 1 WHERE id = 11;
UPDATE posts SET likes = 0 WHERE id = 12;
UPDATE posts SET likes = 1 WHERE id = 13;
UPDATE posts SET likes = 0 WHERE id = 14;
UPDATE posts SET likes = 0 WHERE id = 15;

UPDATE posts SET views = 2 WHERE id = 1;
UPDATE posts SET views = 1 WHERE id = 2;
UPDATE posts SET views = 2 WHERE id = 3;
UPDATE posts SET views = 2 WHERE id = 4;
UPDATE posts SET views = 1 WHERE id = 5;
UPDATE posts SET views = 1 WHERE id = 6;
UPDATE posts SET views = 1 WHERE id = 7;
UPDATE posts SET views = 1 WHERE id = 8;
UPDATE posts SET views = 1 WHERE id = 9;
UPDATE posts SET views = 1 WHERE id = 10;
UPDATE posts SET views = 1 WHERE id = 11;
UPDATE posts SET views = 0 WHERE id = 12;
UPDATE posts SET views = 0 WHERE id = 13;
UPDATE posts SET views = 0 WHERE id = 14;
UPDATE posts SET views = 0 WHERE id = 15;