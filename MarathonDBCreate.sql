—- CONNECT 'jdbc:derby:MurachDB;create=true';
CONNECT 'jdbc:derby:MarathonDB;create=true';

-- drop tables (ignore errors if they don't exist)
DROP TABLE RunnersInfo;

CREATE TABLE RunnersInfo
(
 	Name VARCHAR(20),
	RestPercentage DOUBLE,
	RunnersSpeed	 DOUBLE
);

INSERT INTO RunnersInfo VALUES('Bear',70,80);
INSERT INTO RunnersInfo VALUES('wolf',100,75);
INSERT INTO RunnersInfo VALUES('deer',90,70);
INSERT INTO RunnersInfo VALUES('cayote',60,90);

SELECT * FROM RunnersInfo;

DISCONNECT;