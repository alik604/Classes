library(ggplot2)

getwd()
setwd("/home/darienubuntu/cmpt318")

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

data <- read.table("Dataset3.txt",header = TRUE, sep = ",")
data$day <- as.POSIXlt(data$Date,format = "%d/%m/%Y")$wday
data$month <- format(as.Date(data$Date), "%m")
data$season <- getSeason(data$Date)

#Q3a
sundayMornings <- data[data$day == 0 & strptime(data$Time,"%H:%M:%S") >= strptime("08:00:00","%H:%M:%S")
                       & strptime(data$Time,"%H:%M:%S") < strptime("11:00:00","%H:%M:%S"),]

sundayNights <- data[data$day == 0 & strptime(data$Time,"%H:%M:%S") >= strptime("21:00:00","%H:%M:%S")
                     & strptime(data$Time,"%H:%M:%S") < strptime("24:00:00","%H:%M:%S"),]

avgMorningGAP <- aggregate(sundayMornings$Global_active_power, list(Time=sundayMornings$Time), mean)

avgNightGAP <- aggregate(sundayNights$Global_active_power, list(Time=sundayNights$Time), mean)

MorningPlot <- ggplot()+
  layer(data = avgMorningGAP, mapping = aes(x=Time, y=x), geom = "point",stat="identity", position = position_identity())+
  coord_cartesian() +
  scale_x_discrete(breaks = c("08:00:00","09:00:00","10:00:00")) +
  scale_y_continuous() +
  labs(title = "Average Global Active Power on Sunday Mornings") +
  ylab("Average Global Active Power")

NightPlot <- ggplot()+
  layer(data = avgNightGAP, mapping = aes(x=Time, y=x), geom = "point",stat="identity", position = position_identity())+
  coord_cartesian() +
  scale_x_discrete(breaks = c("21:00:00","22:00:00","23:00:00","24:00:00")) +
  scale_y_continuous() +
  labs(title = "Average Global Active Power on Sunday Nights") +
  ylab("Average Global Active Power")

print(MorningPlot)
print(NightPlot)

#Q3b
avgMorningGAPMonthly <- aggregate(sundayMornings$Global_active_power, list(Month=sundayMornings$month, Time=sundayMornings$Time), mean)
avgMorningGAPSeasonal <- aggregate(sundayMornings$Global_active_power, list(Season=sundayMornings$season, Time=sundayMornings$Time), mean)

stddevMorningGAPMonthly <- aggregate(sundayMornings$Global_active_power, list(Month=sundayMornings$month, Time=sundayMornings$Time), sd)
stdevMorningGAPSeasonal <- aggregate(sundayMornings$Global_active_power, list(Season=sundayMornings$season, Time=sundayMornings$Time), sd)


MorningMonthlyPlot <- ggplot()+
  layer(data = stddevMorningGAPMonthly, mapping = aes(x=Time, y=x, color = Month), geom = "point",stat="identity", position = position_identity())+
  coord_cartesian() +
  scale_x_discrete(breaks = c("08:00:00","09:00:00","10:00:00")) +
  scale_y_continuous() +
  labs(title = "Std Dev for Global Active Power on Sunday Mornings by Month") +
  ylab("Standard Deviation of Global Active Power")

MorningSeasonalPlot <- ggplot()+
  layer(data = stdevMorningGAPSeasonal, mapping = aes(x=Time, y=x, color = Season), geom = "point",stat="identity", position = position_identity())+
  coord_cartesian() +
  scale_x_discrete(breaks = c("08:00:00","09:00:00","10:00:00")) +
  scale_y_continuous() +
  labs(title = "Std Dev for Global Active Power on Sunday Mornings by Season") +
  ylab("Average Global Active Power")

print(MorningMonthlyPlot)
print(MorningSeasonalPlot)


avgNightGAPMonthly <- aggregate(sundayNights$Global_active_power, list(Month=sundayNights$month, Time=sundayNights$Time), mean)
avgNightGAPSeasonal <- aggregate(sundayNights$Global_active_power, list(Season=sundayNights$season, Time=sundayNights$Time), mean)

stdevNightGAPMonthly <- aggregate(sundayNights$Global_active_power, list(Month=sundayNights$month, Time=sundayNights$Time), sd)
stdevNightGAPSeasonal <- aggregate(sundayNights$Global_active_power, list(Season=sundayNights$season, Time=sundayNights$Time), sd)

NightMonthlyPlot <- ggplot()+
  layer(data = stdevNightGAPMonthly, mapping = aes(x=Time, y=x, color = Month), geom = "point",stat="identity", position = position_identity())+
  coord_cartesian() +
  scale_x_discrete(breaks = c("08:00:00","09:00:00","10:00:00")) +
  scale_y_continuous() +
  labs(title = "Std Dev for Global Active Power on Sunday Nights by Month") +
  ylab("Standard Deviation of Global Active Power")

NightSeasonalPlot <- ggplot()+
  layer(data = stdevNightGAPSeasonal, mapping = aes(x=Time, y=x, color = Season), geom = "point",stat="identity", position = position_identity())+
  coord_cartesian() +
  scale_x_discrete(breaks = c("08:00:00","09:00:00","10:00:00")) +
  scale_y_continuous() +
  labs(title = "Std Dev of Global Active Power on Sunday Nights by Season") +
  ylab("Standard Deviation of Global Active Power")

print(NightMonthlyPlot)
print(NightSeasonalPlot)
