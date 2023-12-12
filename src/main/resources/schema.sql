DROP TABLE IF EXISTS VOTE;
DROP TABLE IF EXISTS QUOTE;
DROP TABLE IF EXISTS WEBUSER;

CREATE TABLE webuser (user_id INT auto_increment primary key,
                      name VARCHAR(32) UNIQUE,
                      email VARCHAR(32) UNIQUE,
                      password VARCHAR(1024),
                      created_time TIMESTAMP(0),
                      landing_page VARCHAR(64)
);

CREATE TABLE quote (
                       quote_id INT auto_increment primary key,
                       content VARCHAR(1024),
                       user_id INT,
                       created_time TIMESTAMP(0),
                       total_votes INT,
                       last_voted TIMESTAMP(0),
                       foreign key (user_id) references webuser(user_id)
);


CREATE TABLE vote (    id INT auto_increment primary key,
                       user_id INT,
                       quote_id INT,
                       rate INT,
                       set_time TIMESTAMP(0),
                       foreign key (quote_id) references quote(quote_id)

);