-- CREATE
CREATE TABLE role (
	id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	label VARCHAR(100) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(label)
);

CREATE TABLE profile (
	id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	label VARCHAR(100) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(label)
);

CREATE TABLE profile_role (
	idprofile INTEGER UNSIGNED NOT NULL,
	idrole INTEGER UNSIGNED NOT NULL,
	PRIMARY KEY(idprofile, idrole),
	FOREIGN KEY(idrole) REFERENCES role(id),
	FOREIGN KEY(idprofile) REFERENCES profile(id)
);

CREATE TABLE user (
	id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	lastname VARCHAR(100) NOT NULL,
	firstname VARCHAR(100) NOT NULL,
	mail VARCHAR(100) NOT NULL,
	idprofile INTEGER UNSIGNED NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(idprofile) REFERENCES profile(id)
);

-- INSERT
INSERT INTO role
(id,label) VALUES
(1,'app.access'),
(2,'admin.profile.edit'),
(3,'admin.profile.delete'),
(4,'admin.profile.view'),
(5,'admin.user.edit'),
(6,'admin.user.delete'),
(7,'admin.user.view');

INSERT INTO profile
(id,label) VALUES
(1,'basic'),
(2,'admin'),
(3,'manager');

INSERT INTO profile_role
(idprofile,idrole) VALUES
(1,1),
(2,1),
(2,2),
(2,3),
(2,4),
(2,5),
(2,6),
(2,7),
(3,1),
(3,4),
(3,7);

INSERT INTO user
(id,lastname,firstname,mail,idprofile) VALUES	
(1,'Foo','Basic','foo.basic@wonderland.org',1),
(2,'Bar','Basic','bar.basic@wonderland.org',1),
(3,'Foo','Admin','foo.admin@wonderland.org',2),
(4,'Foo','Manager','foo.manager@wonderland.org',3),
(5,'Bar','Manager','bar.manager@wonderland.org',3),
