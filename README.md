In this experiment, I tried to  manage the relationship between tables and retrieve data from database in 2 ways:
-   The first way, I manually create the relationship between tables. (all entity with affix `M`. Ex: `StudentM`, `SubjectM`,..)
- The second way, I use JPA mapping relationship to create the relationship between tables.


Relationship between tables:

| Relationship   | JPA Mapping             | Manual Mapping            |
|----------------|-------------------------|---------------------------|
| `many-to-many` | `Student` and `Subject` | `StudentM` and `SubjectM` |
| `one-to-many`  | `Teacher` and `Student` | `TeacherM` and `StudentM` |
| `many-to-one`  | `Subject` and `Teacher` | `SubjectM` and `TeacherM` |
| `one-to-one`   | `Library` and `Address` |                           |    
