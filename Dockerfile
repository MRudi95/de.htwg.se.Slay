FROM hseeberger/scala-sbt
WORKDIR /Slay
ADD . /Slay
CMD sbt run
