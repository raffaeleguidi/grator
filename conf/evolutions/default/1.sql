# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table `test` (`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,`name` VARCHAR(254) NOT NULL,`foreign_id` VARCHAR(254) NOT NULL);

# --- !Downs

drop table `test`;

