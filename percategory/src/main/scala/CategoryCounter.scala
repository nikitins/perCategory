import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.{Row, SparkSession}
import java.util.Properties

import org.apache.spark.sql.types.StructType

object CategoryCounter {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("CategoryCounter")
    val sparkContext = new SparkContext(conf)
    val sparkSession = SparkSession.builder().getOrCreate()
    import sparkSession.implicits._

    val connectionProperties = new Properties()
    connectionProperties.put("driver", "com.mysql.jdbc.Driver")
    connectionProperties.put("url", "jdbc:mysql://10.0.0.21:3306/snikitin")
    connectionProperties.put("user", "snikitin")

    val textFiles = sparkContext.textFile("hdfs://" + args(0))

    val splited = textFiles.map(_.split(","))

    val filtered = splited.map(_(3))

    val counted  = filtered.map(x => (x, 1L)).reduceByKey(_ + _)

    val sorted = counted.sortBy(_._2, ascending = false)

    val row = sorted.map(x => Row(x._1, x._2))

    val schema = StructType($"category".string :: $"count".long :: Nil)

    val dataFrame = sparkSession.createDataFrame(row, schema).limit(10)

    dataFrame.write.jdbc(connectionProperties.getProperty("url"), "spark_sales_per_category", connectionProperties)
  }
}