library("depmixS4")
library(ggplot2)
library(xts)

# getwd()
setwd("/Users/Andrei/Desktop/cmpt318/")
data <- read.table("Train Data.txt",header = TRUE, sep = ",")
#data$day <- as.POSIXlt(data$Date,format = "%d/%m/%Y")$wday
data$DateTime <- as.POSIXct(paste(data$Date, data$Time), format="%d/%m/%Y %H:%M:%S", tz="GMT")

#hourly.xts <- xts(data, order.by = as.POSIXlt(paste(data$Date, data$Time), format="%d/%m/%Y %H:%M:%S"))

xtsDF <- xts(data, order.by = as.POSIXct(data$DateTime, format="%y-%m-%d %H:%M:%S"))

hourlyXTS <- period.apply(xtsDF[,3:9], endpoints(xtsDF, "hours"), FUN=mean, , na.rm=TRUE)
dailyDF <- apply.daily(xtsDF[,3:9], mean, na.rm=TRUE)
weeklyDF <- apply.weekly(xtsDF[,3:9], mean, na.rm=TRUE)
monthlyDF <- apply.monthly(xtsDF[,3:9], mean, na.rm=TRUE)

cor(hourlyXTS, use = "complete.obs")
cor(dailyDF, use = "complete.obs")
cor(weeklyDF, use = "complete.obs")
cor(monthlyDF, use = "complete.obs")
