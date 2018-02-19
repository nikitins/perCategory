cd percategory
sbt package
ssh gu_gateway 'rm percategory_2.11-0.2.jar'
scp target/scala-2.11/percategory_2.11-0.2.jar gu_gateway:/home/snikitin/

