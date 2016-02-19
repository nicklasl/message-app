pre-requisites:
- java8
- sbt http://www.scala-sbt.org/
- typesafe-activator (optional)

Start by issuing ```sbt run``` from within the root folder. The play server is listening on port 9000 by default.

```POST /messages``` with json body ```{"recipient":"Luke", "text":"Use the force"}``` sends a message to Luke

```GET /messages/new/Luke``` fetches only new messages for Luke

```GET /messages/Luke?start=0&stop=5``` fetches old and new messages for Luke. Query parameters start and stop are possible. Default start = 0, stop = 5

```DELETE /messages?id=1,2,3``` removes messages with id 1, 2 and 3.
