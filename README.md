# Tasks-Api

APIs for Tasks app

Frontend - https://github.com/SrikantKanekar/TasksApp-Flutter

## API Reference

#### Register

```http
  POST /auth/register
```

#### Login

```http
  POST /auth/login
```

#### Get task by id

```http
  GET /tasks/{index}/{id}
```

#### Add task

```http
  POST /tasks/{index}
```

#### Update task

```http
  PUT /tasks/{index}
```

#### Toggle task completed status

```http
  PUT /tasks/toggle/{index}
```

#### Change task list

```http
  PUT /tasks/change/{index}/{name}
```

#### Delete task

```http
  DELETE /tasks/{index}/task
```

#### Delete completed task

```http
  DELETE /tasks/{index}/completed
```

#### Get tasks list

```http
  GET /task-lists
```

#### Add tasks list

```http
  POST /task-lists
```

#### Rename tasks list

```http
  PUT /task-lists/name/{index}
```

#### Update tasks list order

```http
  PUT /task-lists/order/{index}
```

#### Delete tasks list

```http
  DELETE /task-lists/{id}
```

