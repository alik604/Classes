###############################################

getwd()
setwd("/Users/Amir/Desktop/CMPT318(Fall2018)")

DataDf <- read.table("ggplot2Data.txt", header = T, sep = ",")

library(ggplot2)
 
ggplot()+
  layer(data = DataDf, mapping = aes(x=Time, y=Voltage), geom = "point",stat="identity", position = position_identity())

###############################################

ggplot()+
  layer(data = DataDf, mapping = aes(x=Date, y=Voltage), geom = "point",stat="identity", position = position_identity())

###############################################

ggplot()+
  layer(data = DataDf, mapping = aes(x=Date, y=Voltage), geom = "point",stat="identity", position = position_jitter(width = 0.3, height = 0)) +
  coord_cartesian() +
  scale_x_discrete() +
  scale_y_continuous()

###############################################

ggplot()+
  layer(data = DataDf, mapping = aes(x=Date, y=Voltage), geom = "point",stat="identity", position = position_jitter(width = 0.3, height = 0)) +
  layer(data = DataDf, mapping = aes(x=Date, y=Voltage), geom = "boxplot" ,stat="boxplot", position = position_identity()) +
  coord_cartesian() +
  scale_x_discrete() +
  scale_y_continuous()

###############################################

ggplot()+
  layer(data = DataDf, mapping = aes(x=Time, y=Voltage, color = Date), geom = "point",stat="identity", position = position_identity()) +
  coord_cartesian() +
  scale_x_discrete() +
  scale_y_continuous() +
  scale_color_hue()

###############################################

ggplot()+
  layer(data = DataDf, mapping = aes(x=Time, y=Voltage, color = Global_intensity), geom = "point",stat="identity", position = position_identity()) +
  coord_cartesian() +
  scale_x_discrete() +
  scale_y_continuous() +
  scale_color_continuous() +
  facet_wrap(~Date)

###############################################

ggplot()+
  layer(data = DataDf, mapping = aes(x=Time, y=Voltage, color = Global_intensity), geom = "point",stat="identity", position = position_identity()) +
  coord_cartesian() +
  scale_x_discrete() +
  scale_y_continuous() +
  scale_color_continuous(low = "#0061ff", high = "#00ffe1", name = "Global_Intensity", guide = guide_colorbar(direction = "vertical")) +
  facet_wrap(~Date)

###############################################