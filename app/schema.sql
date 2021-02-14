-- Users(Record hosts' account details )
DROP TABLE Users;
CREATE TABLE Users (
    HID INTEGER PRIMARY KEY AUTOINCREMENT,
    UserName VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(20) NOT NULL,
    FirstName VARCHAR(20) NOT NULL,
    LastName VARCHAR(20) NOT NULL
);

-- Events created by host
DROP TABLE Events;
CREATE TABLE Events(
    EID INTEGER PRIMARY KEY AUTOINCREMENT,
    -- Host ID that the event belongs to
    HostID INTEGER NOT NULL,
    EventName VARCHAR(50) NOT NULL,
    EventPassword VARCHAR(20) NOT NULL,
    StartTime DATETIME NOT NULL,
    FinishTime DATETIME NOT NULL,
    FOREIGN KEY(HostID) REFERENCES Users(HID)
);

DROP TABLE Feedback;
CREATE TABLE Feedback(
    FID INTEGER PRIMARY KEY AUTOINCREMENT,
    EventID INTEGER NOT NULL,
    AttendeeName VARCHAR(50),
    Feedback VARCHAR(65535) NOT NULL,
    Mood TINYINT(1) NOT NULL,
    -- Answer contains JSON text for the answers to the template that host assigned
    Answer VARCHAR(65535),
    Additionals VARCHAR(50),
    FOREIGN KEY(EventID) REFERENCES Events(EID)
);

DROP TABLE Template;
CREATE TABLE Template(
    TID INTEGER PRIMARY KEY AUTOINCREMENT,
    -- Each event can only have one template
    EventID INTEGER NOT NULL UNIQUE,
    -- Question contains JSON text for host-assigned questions
    Question VARCHAR(65535) NOT NULL,
    FOREIGN KEY(EventID) REFERENCES Events(EID)
)