library(ggplot2)
library(zoo)

getwd()
setwd("C:/Users/Stefano/Documents/SFU/CMPT318/Project")

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

#data loading

train <- read.table("Train data.txt",header = TRUE, sep = ",")
train$day <- as.POSIXlt(train$Date,format = "%d/%m/%Y")$wday
train$month <- format(as.Date(train$Date), "%m")
train$season <- getSeason(train$Date)

test1 <- read.table("test1.txt",header = TRUE, sep = ",")
test1$day <- as.POSIXlt(test1$Date,format = "%d/%m/%Y")$wday
test1$month <- format(as.Date(test1$Date), "%m")
test1$season <- getSeason(test1$Date)

test2 <- read.table("test2.txt",header = TRUE, sep = ",")
test2$day <- as.POSIXlt(test2$Date,format = "%d/%m/%Y")$wday
test2$month <- format(as.Date(test2$Date), "%m")
test2$season <- getSeason(test2$Date)

test3 <- read.table("test3.txt",header = TRUE, sep = ",")
test3$day <- as.POSIXlt(test3$Date,format = "%d/%m/%Y")$wday
test3$month <- format(as.Date(test3$Date), "%m")
test3$season <- getSeason(test3$Date)

test4 <- read.table("test4.txt",header = TRUE, sep = ",")
test4$day <- as.POSIXlt(test4$Date,format = "%d/%m/%Y")$wday
test4$month <- format(as.Date(test4$Date), "%m")
test4$season <- getSeason(test4$Date)

test5 <- read.table("test5.txt",header = TRUE, sep = ",")
test5$day <- as.POSIXlt(test5$Date,format = "%d/%m/%Y")$wday
test5$month <- format(as.Date(test5$Date), "%m")
test5$season <- getSeason(test5$Date)

#data partitioning in to wednesday evenings in the summer
xseason <- 2
xday <- 3
timelower <- "19:00:00"
timeupper <- "23:00:00"

summerWednesdayEvenings_tr <- train[train$season == xseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te1 <- test1[test1$season == xseason & test1$day == xday & strptime(test1$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test1$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te2 <- test2[test2$season == xseason & test2$day == xday & strptime(test2$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test2$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te3 <- test3[test3$season == xseason & test3$day == xday & strptime(test3$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test3$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te4 <- test4[test4$season == xseason & test4$day == xday & strptime(test4$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test4$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te5 <- test5[test5$season == xseason & test5$day == xday & strptime(test5$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test5$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

GAP_tr <- summerWednesdayEvenings_tr$Global_active_power
minGAP_tr <- min(GAP_tr, na.rm = T)
maxGAP_tr <- max(GAP_tr, na.rm = T)

PA_summerWednesdayEvenings_te1 <- summerWednesdayEvenings_te1[summerWednesdayEvenings_te1$Global_active_power < minGAP_tr
                                                              | summerWednesdayEvenings_te1$Global_active_power > maxGAP_tr,]

PA_summerWednesdayEvenings_te2 <- summerWednesdayEvenings_te2[summerWednesdayEvenings_te2$Global_active_power < minGAP_tr
                                                              | summerWednesdayEvenings_te2$Global_active_power > maxGAP_tr,]

PA_summerWednesdayEvenings_te3 <- summerWednesdayEvenings_te3[summerWednesdayEvenings_te3$Global_active_power < minGAP_tr
                                                              | summerWednesdayEvenings_te3$Global_active_power > maxGAP_tr,]

PA_summerWednesdayEvenings_te4 <- summerWednesdayEvenings_te4[summerWednesdayEvenings_te4$Global_active_power < minGAP_tr
                                                              | summerWednesdayEvenings_te4$Global_active_power > maxGAP_tr,]

PA_summerWednesdayEvenings_te5 <- summerWednesdayEvenings_te5[summerWednesdayEvenings_te5$Global_active_power < minGAP_tr
                                                              | summerWednesdayEvenings_te5$Global_active_power > maxGAP_tr,]

#compute mean global active power grouped by times of day
avgSWE_GAP_tr <- aggregate(summerWednesdayEvenings_tr$Global_active_power, list(Time=summerWednesdayEvenings_tr$Time), mean)
avgSWE_GAP_te1 <- aggregate(summerWednesdayEvenings_te1$Global_active_power, list(Time=summerWednesdayEvenings_te1$Time), mean)
avgSWE_GAP_te2 <- aggregate(summerWednesdayEvenings_te2$Global_active_power, list(Time=summerWednesdayEvenings_te2$Time), mean)
avgSWE_GAP_te3 <- aggregate(summerWednesdayEvenings_te3$Global_active_power, list(Time=summerWednesdayEvenings_te3$Time), mean)
avgSWE_GAP_te4 <- aggregate(summerWednesdayEvenings_te4$Global_active_power, list(Time=summerWednesdayEvenings_te4$Time), mean)
avgSWE_GAP_te5 <- aggregate(summerWednesdayEvenings_te5$Global_active_power, list(Time=summerWednesdayEvenings_te5$Time), mean)

#order for consistency of elements
avgSWE_GAP_tr <- avgSWE_GAP_tr[order(avgSWE_GAP_tr$Time, decreasing = FALSE),]
avgSWE_GAP_te1 <- avgSWE_GAP_te1[order(avgSWE_GAP_te1$Time, decreasing = FALSE),]
avgSWE_GAP_te2 <- avgSWE_GAP_te2[order(avgSWE_GAP_te2$Time, decreasing = FALSE),]
avgSWE_GAP_te3 <- avgSWE_GAP_te3[order(avgSWE_GAP_te3$Time, decreasing = FALSE),]
avgSWE_GAP_te4 <- avgSWE_GAP_te4[order(avgSWE_GAP_te4$Time, decreasing = FALSE),]
avgSWE_GAP_te5 <- avgSWE_GAP_te5[order(avgSWE_GAP_te5$Time, decreasing = FALSE),]

avgSWE_GAP_tr$roll <- rollapplyr(as.matrix(avgSWE_GAP_tr$x), 7, mean, partial = TRUE)

pa_te1_plot <- ggplot() +
  layer(data = summerWednesdayEvenings_te1, 
        mapping = aes(x=Time, y=summerWednesdayEvenings_te1$Global_active_power, color = 'Test Data1'), 
        geom = 'point', stat="identity", position = position_identity())+
  layer(data = PA_summerWednesdayEvenings_te1,
        mapping = aes(x=Time, y=PA_summerWednesdayEvenings_te1$Global_active_power, color = 'Point Anomalies'),
        geom = 'point', stat="identity", position = position_identity()) +
  scale_colour_manual("", 
                      breaks = c("Point Anomalies", "Test Data1"),
                      values = c("red", "black")) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  labs(title = "Global Active Power on Summer Wednesday Evenings") +
  ylab("Global Active Power")
print(pa_te1_plot)

pa_te2_plot <- ggplot() +
  layer(data = summerWednesdayEvenings_te2, 
        mapping = aes(x=Time, y=summerWednesdayEvenings_te2$Global_active_power, color = 'Test Data2'), 
        geom = 'point', stat="identity", position = position_identity())+
  layer(data = PA_summerWednesdayEvenings_te2,
        mapping = aes(x=Time, y=PA_summerWednesdayEvenings_te2$Global_active_power, color = 'Point Anomalies'),
        geom = 'point', stat="identity", position = position_identity()) +
  scale_colour_manual("", 
                      breaks = c("Point Anomalies", "Test Data2"),
                      values = c("red", "black")) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  labs(title = "Global Active Power on Summer Wednesday Evenings") +
  ylab("Global Active Power")
print(pa_te2_plot)

pa_te3_plot <- ggplot() +
  layer(data = summerWednesdayEvenings_te3, 
        mapping = aes(x=Time, y=summerWednesdayEvenings_te3$Global_active_power, color = 'Test Data3'), 
        geom = 'point', stat="identity", position = position_identity())+
  layer(data = PA_summerWednesdayEvenings_te3,
        mapping = aes(x=Time, y=PA_summerWednesdayEvenings_te3$Global_active_power, color = 'Point Anomalies'),
        geom = 'point', stat="identity", position = position_identity()) +
  scale_colour_manual("", 
                      breaks = c("Point Anomalies", "Test Data3"),
                      values = c("red", "black")) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  labs(title = "Global Active Power on Summer Wednesday Evenings") +
  ylab("Global Active Power")
print(pa_te3_plot)

pa_te4_plot <- ggplot() +
  layer(data = summerWednesdayEvenings_te4, 
        mapping = aes(x=Time, y=summerWednesdayEvenings_te4$Global_active_power, color = 'Test Data4'), 
        geom = 'point', stat="identity", position = position_identity())+
  layer(data = PA_summerWednesdayEvenings_te4,
        mapping = aes(x=Time, y=PA_summerWednesdayEvenings_te4$Global_active_power, color = 'Point Anomalies'),
        geom = 'point', stat="identity", position = position_identity()) +
  scale_colour_manual("", 
                      breaks = c("Point Anomalies", "Test Data4"),
                      values = c("red", "black")) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  labs(title = "Global Active Power on Summer Wednesday Evenings") +
  ylab("Global Active Power")
print(pa_te4_plot)

pa_te5_plot <- ggplot() +
  layer(data = summerWednesdayEvenings_te5, 
        mapping = aes(x=Time, y=summerWednesdayEvenings_te5$Global_active_power, color = 'Test Data5'), 
        geom = 'point', stat="identity", position = position_identity())+
  layer(data = PA_summerWednesdayEvenings_te5,
        mapping = aes(x=Time, y=PA_summerWednesdayEvenings_te5$Global_active_power, color = 'Point Anomalies'),
        geom = 'point', stat="identity", position = position_identity()) +
  scale_colour_manual("", 
                      breaks = c("Point Anomalies", "Test Data5"),
                      values = c("red", "black")) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  labs(title = "Global Active Power on Summer Wednesday Evenings") +
  ylab("Global Active Power")
print(pa_te5_plot)

te1_plot <- ggplot(data = avgSWE_GAP_tr, aes(x = Time, group = 1)) +
        geom_point(data =avgSWE_GAP_te1, aes(x = Time, y = x, color = 'Test Data1')) +
        geom_line(aes(y = roll, color = 'Rolling Average of Training Data'), size = 1) +
        scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
        scale_y_continuous() +
        scale_colour_manual("", 
                            breaks = c("Rolling Average of Training Data", "Test Data1"),
                            values = c("red", "black")) +
        labs(title = "Average Global Active Power on Summer Wednesday Evenings") +
        ylab("Average Global Active Power")
print(te1_plot)

te2_plot <- ggplot(data = avgSWE_GAP_tr, aes(x = Time, group = 1)) +
  geom_point(data =avgSWE_GAP_te2, aes(x = Time, y = x, color = 'Test Data2')) +
  geom_line(aes(y = roll, color = 'Rolling Average of Training Data'), size = 1) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  scale_colour_manual("", 
                      breaks = c("Rolling Average of Training Data", "Test Data2"),
                      values = c("red", "black")) +
  labs(title = "Average Global Active Power on Summer Wednesday Evenings") +
  ylab("Average Global Active Power")
print(te2_plot)

te3_plot <- ggplot(data = avgSWE_GAP_tr, aes(x = Time, group = 1)) +
  geom_point(data =avgSWE_GAP_te3, aes(x = Time, y = x, color = 'Test Data3')) +
  geom_line(aes(y = roll, color = 'Rolling Average of Training Data'), size = 1) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  scale_colour_manual("", 
                      breaks = c("Rolling Average of Training Data", "Test Data3"),
                      values = c("red", "black")) +
  labs(title = "Average Global Active Power on Summer Wednesday Evenings") +
  ylab("Average Global Active Power")
print(te3_plot)

te4_plot <- ggplot(data = avgSWE_GAP_tr, aes(x = Time, group = 1)) +
  geom_point(data =avgSWE_GAP_te4, aes(x = Time, y = x, color = 'Test Data4')) +
  geom_line(aes(y = roll, color = 'Rolling Average of Training Data'), size = 1) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  scale_colour_manual("", 
                      breaks = c("Rolling Average of Training Data", "Test Data4"),
                      values = c("red", "black")) +
  labs(title = "Average Global Active Power on Summer Wednesday Evenings") +
  ylab("Average Global Active Power")
print(te4_plot)

te5_plot <- ggplot(data = avgSWE_GAP_tr, aes(x = Time, group = 1)) +
  geom_point(data =avgSWE_GAP_te5, aes(x = Time, y = x, color = 'Test Data5')) +
  geom_line(aes(y = roll, color = 'Rolling Average of Training Data'), size = 1) +
  scale_x_discrete(breaks = c("19:00:00","20:00:00","21:00:00","22:00:00")) +
  scale_y_continuous() +
  scale_colour_manual("", 
                      breaks = c("Rolling Average of Training Data", "Test Data5"),
                      values = c("red", "black")) +
  labs(title = "Average Global Active Power on Summer Wednesday Evenings") +
  ylab("Average Global Active Power")
print(te5_plot)