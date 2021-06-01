create database voice;

use voice;

create table member
(
    memberIdx       int not null auto_increment primary key,
    memberImage varchar(100),
    memberId     varchar(20) not null,
    memberPassword varchar(20) not null,
    memberGrade int not null default 0
);

create table board
(
    boardIdx int not null auto_increment primary key,
    boardImage varchar(100),
    boardAudio varchar(100) not null,
    memberIdx int not null,
    boardDate datetime not null default now(),
    foreign key (memberIdx) references member (memberIdx)
);

create table board_comment
(
    commentIdx int not null auto_increment primary key,
    commentText varchar(2048),
    memberIdx int not null,
    boardIdx int not null,
    commentDate datetime not null default now(),
    foreign key (memberIdx) references member (memberIdx),
    foreign key (boardIdx) references board (boardIdx)
);

create table follow
(
    followIdx int not null auto_increment primary key,
    -- 팔로우 한 사람
    memberIdxFollower int not null,
    -- 팔로우 된 사람
    memberIdxFollowing int not null,
    foreign key (memberIdxFollower) references member (memberIdx),
    foreign key (memberIdxFollowing) references member (memberIdx)
);

create table likes
(
    likeIdx int not null auto_increment primary key,
    memberIdx int not null,
    boardIdx int not null,
    foreign key (memberIdx) references member (memberIdx),
    foreign key (boardIdx) references board (boardIdx)
);