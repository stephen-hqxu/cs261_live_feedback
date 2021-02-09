-- Users(Record hosts' account details )
DROP TABLE Users;
CREATE TABLE Users (
    HID INTEGER PRIMARY KEY AUTOINCREMENT,
    Username VARCHAR(50),
    Pwd VARCHAR(20)
);

-- Events created by host
DROP TABLE Events;
CREATE TABLE Events(
    EID INTEGER PRIMARY KEY AUTOINCREMENT,
    HostID INTEGER,
    EventName VARCHAR(50),
    EventPwd VARCHAR(20),
    StartTime DATETIME,
    FinishTime DATETIME,
    FOREIGN KEY(HostID) REFERENCES Users(HID)
);

DROP TABLE Feedback;
CREATE TABLE Feedback(
    FID INTEGER PRIMARY KEY AUTOINCREMENT,
    EventID INTEGER,
    Name VARCHAR(50),
    Feedback VARCHAR(65535),
    Mood TINYINT(1),
    Answer VARCHAR(65535),
    Additionals VARCHAR(50),
    FOREIGN KEY(EventID) REFERENCES Events(EID)
);

DROP TABLE Template;
CREATE TABLE Template(
    TID INTEGER PRIMARY KEY AUTOINCREMENT,
    EventID INTEGER,
    Question VARCHAR(65535),
    FOREIGN KEY(EventID) REFERENCES Events(EID)
)