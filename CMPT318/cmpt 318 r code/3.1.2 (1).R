library("depmixS4")
library(ggplot2)

# getwd()
setwd("R workspace/final")

#getseason function
#https://stackoverflow.com/questions/9500114/find-which-season-a-particular-date-belongs-to
getSeason <- function(DATES) {
  WS <- as.Date("2012-12-15", format = "%Y-%m-%d") # Winter Solstice
  SE <- as.Date("2012-3-15",  format = "%Y-%m-%d") # Spring Equinox
  SS <- as.Date("2012-6-15",  format = "%Y-%m-%d") # Summer Solstice
  FE <- as.Date("2012-9-15",  format = "%Y-%m-%d") # Fall Equinox
  
  # Convert dates from any year to 2012 dates
  d <- as.Date(strftime(DATES, format="2012-%m-%d"))
  
  ifelse (d >= WS | d < SE, 4,
          ifelse (d >= SE & d < SS, 1,
                  ifelse (d >= SS & d < FE, 2, 3)))
}

data <- read.table("Train Data.txt",header = TRUE, sep = ",")

data$Date <- as.POSIXct(data$Date, format="%d/%m/%Y")
data$Date <- format(data$Date, format("%m/%d"))

aggregateDF <- aggregate(data$Voltage, list(Date=data$Date, Time=data$Time), mean)

aggregateDF$Date <- as.POSIXct(aggregateDF$Date, format="%m/%d")
aggregateDF$season <- getSeason(aggregateDF$Date)

earlyMorningStart <- strptime("08:00:00", format="%H:%M:%S") 
earlyMorningEnd <- strptime("08:01:00", format="%H:%M:%S")  

filterDF <- split(aggregateDF, 
                     (strptime(aggregateDF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                       (strptime(aggregateDF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                       (aggregateDF$season == 1),
                     drop = TRUE)

# This doesn't change 
earlyMornSpringDF <- data.frame(Date = as.POSIXct(paste(filterDF[[2]][[1]], filterDF[[2]][[2]]), format="%Y-%m-%d %H:%M:%S"),
                          Voltage = filterDF[[2]][[3]])

earlyMornSpringLM <- lm(Voltage ~ Date, data=earlyMornSpringDF)
summary(earlyMornSpringLM)

test1DF <- read.table("test1.txt",header = TRUE, sep = ",")

test1DF$Date <- as.POSIXct(test1DF$Date, format="%d/%m/%Y")
test1DF$Date <- format(test1DF$Date, format("%m/%d"))
test1DF$Date <- as.POSIXct(test1DF$Date, format="%m/%d")
test1DF$season <- getSeason(test1DF$Date)

filterDF <- split(test1DF, 
                  (strptime(test1DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test1DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test1DF$season == 1),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test1DF <- data.frame(Date = as.POSIXct(paste(filterDF[[2]][[1]], filterDF[[2]][[2]]), format="%Y-%m-%d %H:%M:%S"),
                      Voltage = filterDF[[2]][[5]])

earlyMornSpringPrediction <- predict(earlyMornSpringLM, test1DF)
actual_earlyMornSpringPrediction <- data.frame(cbind(actuals=test1DF, predicteds=earlyMornSpringPrediction))

correlation_accuracy <- cor(actual_earlyMornSpringPrediction$actuals.Voltage, actual_earlyMornSpringPrediction$predicteds, use="complete.obs")
head(actual_earlyMornSpringPrediction)
