insert into author (id, first_name, last_name)
values (1, 'John', 'Steinbeck');

insert into book (id, isbn, publisher, title, author_id)
values (1, '978-1617294945', 'Simon & Schuster',
        'Spring in Action, 5th Edition', (select id from author where first_name = 'Craig' and last_name = 'Walls'));

insert into book (id, isbn, publisher, title, author_id)
values (2, '978-1617292545', 'Simon & Schuster',
        'Spring Boot in Action, 1st Edition',
        (select id from author where first_name = 'Craig' and last_name = 'Walls'));

insert into book (id, isbn, publisher, title, author_id)
values (3, '978-1617297571', 'Simon & Schuster',
        'Spring in Action, 6th Edition', (select id from author where first_name = 'Craig' and last_name = 'Walls'));

insert into author (id, first_name, last_name)
values (2, 'John', 'Evans');

insert into book (id, isbn, publisher, title, author_id)
values (4, '978-0321125217', 'Addison Wesley',
        'Domain-Driven Design', (select id from author where first_name = 'Eric' and last_name = 'Evans'));

insert into author (id, first_name, last_name)
values (3, 'Robert', 'Martin');

insert into book (id, isbn, publisher, title, author_id)
values (5, '978-0134494166', 'Addison Wesley',
        'Clean Code', (select id from author where first_name = 'Robert' and last_name = 'Martin'));
