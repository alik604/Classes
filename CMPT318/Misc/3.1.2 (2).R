library("depmixS4")
library(ggplot2)

# getwd()
setwd("/Users/Andrei/Desktop/cmpt318/")

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

aggregateDF <- aggregate(data$Global_intensity, list(Date=data$Date, Time=data$Time), mean)

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
                          Global_intensity = filterDF[[2]][[3]])

earlyMornSpringLM <- lm(Global_intensity ~ Date, data=earlyMornSpringDF)
summary(earlyMornSpringLM)

### TEST 1 ### 

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
                      Global_intensity = filterDF[[2]][[6]])

test1_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test1DF)
#test1_actual_earlyMornSpringPrediction <- data.frame(cbind(actuals=test1DF, predicteds=earlyMornSpringPrediction))

#test1_correlation_accuracy <- cor(actual_earlyMornSpringPrediction$Global_intensity, actual_earlyMornSpringPrediction$predicteds, use="complete.obs")
#head(actual_earlyMornSpringPrediction)

### TEST 2 ### 

test2DF <- read.table("test2.txt",header = TRUE, sep = ",")

test2DF$Date <- as.POSIXct(test2DF$Date, format="%d/%m/%Y")
test2DF$Date <- format(test2DF$Date, format("%m/%d"))
test2DF$Date <- as.POSIXct(test2DF$Date, format="%m/%d")
test2DF$season <- getSeason(test2DF$Date)

filterDF <- split(test2DF, 
                  (strptime(test2DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test2DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test2DF$season == 1),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test2DF <- data.frame(Date = as.POSIXct(paste(filterDF[[2]][[1]], filterDF[[2]][[2]]), format="%Y-%m-%d %H:%M:%S"),
                      Global_intensity = filterDF[[2]][[6]])

test2_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test2DF)

### TEST 3 ### 

test3DF <- read.table("test3.txt",header = TRUE, sep = ",")

test3DF$Date <- as.POSIXct(test3DF$Date, format="%d/%m/%Y")
test3DF$Date <- format(test3DF$Date, format("%m/%d"))
test3DF$Date <- as.POSIXct(test3DF$Date, format="%m/%d")
test3DF$season <- getSeason(test3DF$Date)

filterDF <- split(test3DF, 
                  (strptime(test3DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test3DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test3DF$season == 1),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test3DF <- data.frame(Date = as.POSIXct(paste(filterDF[[2]][[1]], filterDF[[2]][[2]]), format="%Y-%m-%d %H:%M:%S"),
                      Global_intensity = filterDF[[2]][[6]])

test3_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test3DF)

### TEST 4 ### 

test4DF <- read.table("test4.txt",header = TRUE, sep = ",")

test4DF$Date <- as.POSIXct(test4DF$Date, format="%d/%m/%Y")
test4DF$Date <- format(test4DF$Date, format("%m/%d"))
test4DF$Date <- as.POSIXct(test4DF$Date, format="%m/%d")
test4DF$season <- getSeason(test4DF$Date)

filterDF <- split(test4DF, 
                  (strptime(test4DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test4DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test4DF$season == 1),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test4DF <- data.frame(Date = as.POSIXct(paste(filterDF[[2]][[1]], filterDF[[2]][[2]]), format="%Y-%m-%d %H:%M:%S"),
                      Global_intensity = filterDF[[2]][[6]])

test4_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test4DF)

### TEST 5 ### 

test5DF <- read.table("test5.txt",header = TRUE, sep = ",")

test5DF$Date <- as.POSIXct(test5DF$Date, format="%d/%m/%Y")
test5DF$Date <- format(test5DF$Date, format("%m/%d"))
test5DF$Date <- as.POSIXct(test5DF$Date, format="%m/%d")
test5DF$season <- getSeason(test5DF$Date)

filterDF <- split(test5DF, 
                  (strptime(test5DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test5DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test5DF$season == 1),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test5DF <- data.frame(Date = as.POSIXct(paste(filterDF[[2]][[1]], filterDF[[2]][[2]]), format="%Y-%m-%d %H:%M:%S"),
                      Global_intensity = filterDF[[2]][[6]])

test5_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test5DF)

### SUMMARY ### 

actual_earlyMornSpringPrediction <- data.frame(cbind(test1=test1DF$Global_intensity, 
                                                     test2=test2DF$Global_intensity, 
                                                     test3=test3DF$Global_intensity, 
                                                     test4=test4DF$Global_intensity, 
                                                     test5=test5DF$Global_intensity, 
                                                     predicteds=test1_earlyMornSpringPrediction))