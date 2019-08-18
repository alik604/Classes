library("depmixS4")
library(plyr)

getwd()
setwd("/home/darienubuntu/cmpt318/FinalProject")
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

train <- read.table("Train Data.txt",header = TRUE, sep = ",")
train$day <- as.POSIXlt(train$Date,format = "%d/%m/%Y")$wday
train$month <- format(as.Date(train$Date), "%m")
train$season <- getSeason(train$Date)


#data partitioning in to wednesday evenings in the summer
xseason <- 1
xxseason <- 2
xxxseason <- 3
xxxxseason <- 4
xday <- 3
timelower <- "19:00:00"
timeupper <- "23:00:00"

springWednesdayEvenings_tr <- train[train$season == xseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]
summerWednesdayEvenings_tr <- train[train$season == xxseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]
fallWednesdayEvenings_tr <- train[train$season == xxxseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                  & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]
winterWednesdayEvenings_tr <- train[train$season == xxxxseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

Nmin <- 10
Nmax <- 20
Nstates <- Nmin:Nmax
BIC <- vector("list", Nmax - Nmin + 1)
ct <- count(summerWednesdayEvenings_tr, "Date")
ct <- ct[order(as.Date(ct$Date, format = "%d/%m/%Y")),]

set.seed(2)
for (n in Nstates)
{
  mod <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_tr, nstates = n, ntimes = ct$freq)
  
  fm <- fit(mod)
  
  BIC[[n-Nmin+1]] <- BIC(fm)
}

plot(Nstates, BIC, ty="b")