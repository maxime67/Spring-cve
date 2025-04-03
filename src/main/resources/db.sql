-- Create the User table (using quotes around the reserved keyword)
CREATE TABLE "User" (
                        UserID BIGINT PRIMARY KEY,
                        Name VARCHAR(255) NOT NULL,
                        Password VARCHAR(255) NOT NULL,
                        PhoneNumber VARCHAR(50),
                        EmailAddress VARCHAR(255)
);

-- Create the Version table
CREATE TABLE "Version" (
                           ID BIGINT PRIMARY KEY,
                           Number VARCHAR(50) NOT NULL
);

-- Create the Technologies table
CREATE TABLE "Technologies" (
                                TechID BIGINT PRIMARY KEY,
                                Name VARCHAR(255) NOT NULL
);

-- Create the Subscriptions table
CREATE TABLE "Subscriptions" (
                                 SubscriptionID BIGINT PRIMARY KEY,
                                 UserID BIGINT NOT NULL,
                                 TechID BIGINT NOT NULL,
                                 SubscriptionDate DATE NOT NULL,
                                 FOREIGN KEY (UserID) REFERENCES "User"(UserID),
                                 FOREIGN KEY (TechID) REFERENCES "Technologies"(TechID)
);

-- Create indexes for the foreign keys to improve query performance
CREATE INDEX idx_subscriptions_userid ON "Subscriptions"(UserID);
CREATE INDEX idx_subscriptions_techid ON "Subscriptions"(TechID);

-- Create relationships between Version and Technologies (many-to-many)
CREATE TABLE "TechnologyVersions" (
                                      TechID BIGINT NOT NULL,
                                      VersionID BIGINT NOT NULL,
                                      PRIMARY KEY (TechID, VersionID),
                                      FOREIGN KEY (TechID) REFERENCES "Technologies"(TechID),
                                      FOREIGN KEY (VersionID) REFERENCES "Version"(ID)
);

-- Create indexes for the many-to-many relationship
CREATE INDEX idx_techversions_techid ON "TechnologyVersions"(TechID);
CREATE INDEX idx_techversions_versionid ON "TechnologyVersions"(VersionID);

-- Sample data insertion (optional)
INSERT INTO "User" VALUES (1, 'John Doe', 'hashed_password', '555-123-4567', 'john@example.com');
INSERT INTO "Technologies" VALUES (1, 'Cloud Computing');
INSERT INTO "Version" VALUES (1, '1.0');
INSERT INTO "Subscriptions" VALUES (1, 1, 1, '2025-04-02');
INSERT INTO "TechnologyVersions" VALUES (1, 1);