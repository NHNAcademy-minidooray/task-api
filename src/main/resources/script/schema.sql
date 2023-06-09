drop table if exists Comments;
drop table if exists TaskTags;
drop table if exists Tasks;
drop table if exists Milestones;
drop table if exists Tags;
drop table if exists ProjectMembers;
drop table if exists Projects;
drop table if exists StatusCodes;



CREATE TABLE IF NOT EXISTS StatusCodes
(
    `status_code_seq`  INT         NOT NULL AUTO_INCREMENT,
    `status_code_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`status_code_seq`)
);


CREATE TABLE IF NOT EXISTS Projects
(
    `project_seq`        INT          NOT NULL AUTO_INCREMENT,
    `project_title`      VARCHAR(50)  NOT NULL,
    `status_code_seq`    INT          NOT NULL,
    `project_content`    VARCHAR(100) NULL DEFAULT NULL,
    `project_created_at` DATETIME     NOT NULL,
    PRIMARY KEY (`project_seq`),
    CONSTRAINT `fk_Project_StatusCode1`
        FOREIGN KEY (`status_code_seq`)
            REFERENCES StatusCodes (`status_code_seq`)
);


CREATE TABLE IF NOT EXISTS Tags
(
    `tag_seq`     INT         NOT NULL AUTO_INCREMENT,
    `project_seq` INT         NOT NULL,
    `tag_name`    VARCHAR(45) NOT NULL,
    PRIMARY KEY (`tag_seq`),
    CONSTRAINT `fk_Tag_Project1`
        FOREIGN KEY (`project_seq`)
            REFERENCES Projects (`project_seq`)
);



CREATE TABLE IF NOT EXISTS Milestones
(
    `milestone_seq`           INT          NOT NULL AUTO_INCREMENT,
    `project_seq`             INT          NOT NULL,
    `milestone_name`          VARCHAR(100) NOT NULL,
    `milestone_start_period`  DATETIME     NULL DEFAULT NULL,
    `milestone_end_of_period` DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`milestone_seq`),
    CONSTRAINT `fk_Milestone_Project1`
        FOREIGN KEY (`project_seq`)
            REFERENCES Projects (`project_seq`)
);


CREATE TABLE IF NOT EXISTS ProjectMembers
(
    `project_member_seq` INT NOT NULL AUTO_INCREMENT,
    `project_member_id`   VARCHAR(20) NOT NULL,
    `project_seq`         INT         NOT NULL,
    `project_member_role` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`project_member_seq`),
    CONSTRAINT `FK_Project_TO_Project_Account_1`
        FOREIGN KEY (`project_seq`)
            REFERENCES Projects (`project_seq`)
);



CREATE TABLE IF NOT EXISTS Tasks
(
    `task_seq`           INT         NOT NULL AUTO_INCREMENT,
    `project_member_seq` VARCHAR(20) NOT NULL,
    `task_title`         VARCHAR(45) NOT NULL,
    `task_content`       TEXT        NOT NULL,
    `task_deadline`      DATETIME    NULL DEFAULT NULL,
    `task_created_at`    DATETIME    NOT NULL,
    `milestone_seq`      INT         NULL,
    PRIMARY KEY (`task_seq`),
    CONSTRAINT `fk_Task_Milestone1`
        FOREIGN KEY (`milestone_seq`)
            REFERENCES Milestones (`milestone_seq`),
    CONSTRAINT `fk_Task_ProjectMember1`
        FOREIGN KEY (`project_member_seq`)
            REFERENCES ProjectMembers (`project_member_seq`)
);


CREATE TABLE IF NOT EXISTS TaskTags
(
    `tag_seq`  INT NOT NULL,
    `task_seq` INT NOT NULL,
    PRIMARY KEY (`tag_seq`, `task_seq`),
    CONSTRAINT `fk_TaskTag_Tag`
        FOREIGN KEY (`tag_seq`)
            REFERENCES Tags (`tag_seq`)
             on delete cascade ,
    CONSTRAINT `fk_TaskTag_Task1`
        FOREIGN KEY (`task_seq`)
            REFERENCES Tasks (`task_seq`)
            on delete  cascade
);

CREATE TABLE IF NOT EXISTS Comments
(
    `comment_seq`        INT  NOT NULL AUTO_INCREMENT,
    `task_seq`           INT  NOT NULL,
    `comment_content`    TEXT NOT NULL,
    `project_member_seq` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`comment_seq`),
    CONSTRAINT `FK_Task_TO_Comment_1`
        FOREIGN KEY (`task_seq`)
            REFERENCES Tasks (`task_seq`),
    CONSTRAINT `fk_Comment_ProjectMember1`
        FOREIGN KEY (`project_member_seq`)
            REFERENCES ProjectMembers (`project_member_seq`)
);



-- status code data insert
merge into StatusCodes values(1, '활성');
merge into StatusCodes values(2, '휴면');
merge into StatusCodes values(3, '종료');


-- project data insert
merge into Projects values(1, 'mini-dooray-project-test', 1, 'test-test-test', now());
merge into Projects values(2, 'dooray-study', 1, 'study', now());


-- tag data insert
merge into Tags values (1, 1, 'Api');
merge into Tags values (2, 1, 'DB');
merge into Tags values (3, 1, 'Thymeleaf');
merge into Tags values (4, 2, 'RestTemplate');
merge into Tags values (5, 2, 'stringBoot');
merge into Tags values (6, 2, 'CS');


-- milestone data insert
merge into Milestones values (1, 1, 'ERD 설계','2023-05-02','2023-05-03');
merge into Milestones values (2, 1, 'API설계','2023-05-03','2023-05-05');
merge into Milestones values (3, 2, 'MVC설계','2023-05-05','2023-05-09');
merge into Milestones (milestone_seq, project_seq, milestone_name) values (4, 2, '전산학이론');


-- project member data insert
merge into ProjectMembers values (1, 'goback10000',2,'ROLE_ADMIN');
merge into ProjectMembers values (2, 'goyoungeun',1,'ROLE_ADMIN');

merge into ProjectMembers values (3, 'mooneunji',1,'ROLE_MEMBER');
merge into ProjectMembers values (4, 'kimsiyeon',1,'ROLE_MEMBER');
merge into ProjectMembers values (5, 'jeongzbum',1,'ROLE_MEMBER');

merge into ProjectMembers values (6, 'mooneunji',2,'ROLE_MEMBER');
merge into ProjectMembers values (7, 'kimsiyeon',2,'ROLE_MEMBER');
merge into ProjectMembers values (8, 'jeongzbum',2,'ROLE_MEMBER');
merge into ProjectMembers values (9, 'goyoungeun',2,'ROLE_MEMBER');


-- task data insert
merge into Tasks values (1, 1 ,'NHN아카데미 전산학기초 특강','전산학기초 특강 - 시간 : 13:00 ~','2023-05-05','2023-05-05',4);
merge into Tasks
values (2,2,'기술요구사항','View 는 Thymeleaf 로 개발합니다.Data Access Layer 는 MyBatis 또는 JPA 를 결정하여 사용합니다. 혼용하지 않습니다.',
        '2023-05-07','2023-05-07',null);
merge into Tasks
values (3,3,'gateway','gateway 는 모든 서비스 요청을 받으며 프레젠테이션 기능을 담당합니다.',null,now(),4);
merge into Tasks values (4,4,'Account-api','// 회원리스트 조회 GET /accounts // 회원 조회 GET /accounts/{id}','2023-05-05','2023-05-05',1);


-- task-tag data insert
merge into TaskTags values (6,1);
merge into TaskTags values (2,1);
merge into TaskTags values (5,2);
merge into TaskTags values (1,3);
merge into TaskTags values (1,4);


-- comment data insert
merge into Comments values (1,1,'3시간 명강의 감사합니다.', 5);
merge into Comments values (2,2,'제가 뷰를 해보겠습니다.', 4);
merge into Comments values (3,4,'응답 바디 만들어주세요', 2);
