FROM hseeberger/scala-sbt
WORKDIR /Slay
ADD . /Slay
CMD sbt run

# docker run -a stdin -a stdout -i slay:v1