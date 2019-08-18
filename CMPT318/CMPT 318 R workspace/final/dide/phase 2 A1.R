library(ggplot2)
library(RcppRoll)

# getwd()
setwd("R workspace/final")
data <- read.table("Train Data.txt", header = TRUE, sep = ",")
#data$day <- as.POSIXlt(data$Date,format = "%d/%m/%Y")$wday

timeDate <- data$Time
#print(timeDate)

GAP <- data$Global_active_power
GAPmean <- mean(GAP, na.rm = TRUE)
GAPmin <- min(GAP, na.rm = T)
GAPmax <- max(GAP, na.rm = T)



GRP <- data$Global_reactive_power
GRPmean <- mean(GRP, na.rm = TRUE)
GRPmin <- min(GRP, na.rm = T)
GRPmax <- max(GRP, na.rm = T)


VOL <- data$Voltage
VOLmean <- mean(VOL, na.rm = TRUE)
VOLmin <- min(data$Voltage, na.rm = T)
VOLmax <- max(data$Voltage, na.rm = T)

GI <- data$Global_intensity
GImean <-  mean(GI, na.rm = TRUE)
GImin <- min(GI, na.rm = T)
GImax <- max(GI, na.rm = T)


print(paste("Arithmetic mean Global active power: ", GAPmean))
print(paste("min Global active power: ", GAPmin))
print(paste("max Global active power: ", GAPmax))

print(paste("Arithmetic mean Global reactive power: ", GRPmean))
print(paste("min Global reactive power: ", GRPmin))
print(paste("max Global reactive power: ", GRPmax))  # only 1.39... thats odd...



print(paste("Arithmetic mean Voltage: ", VOLmean))
print(paste("min Voltage: ", VOLmin))
print(paste("max Voltage: ", VOLmax))


print(paste("Arithmetic mean Global intensiy: ", GImean))
print(paste("min Global intensiy: ", GImin))
print(paste("max Global intensiy: ", GImax))



# usage: arry[i] = rollingAvg(GI[i]..GI[n])
## NOTE: not the intuitive way which is the above being set to arr[n]... which would imply the first n-1 being 0
### sample: print(head(roll_mean(GI, n = 7L, na.rm = TRUE),40))

GIroll <- roll_mean(GI, n = 7L, na.rm = TRUE)

print(head(timeDate, 40))
print(head(GI, 40))
print(head(GIroll, 40))
#plot(unlist(head(GI,30)),unlist(head(timeDate,30)))
# plot with https://www.statmethods.net/advgraphs/ggplot2.html
qplot(
    x = head(timeDate, 30),
    y = head(GI, 30),
    xlab = "time",
    ylab = "GI"
) +   geom_line(aes(y = head(GIroll, 30), color = "disp"))
qplot(
    x = head(timeDate, 30),
    y = head(GIroll, 30),
    xlab = "time",
    ylab = "GI rolling mean 7"
)

## lets test...

test1 <- read.table("test1.txt", header = TRUE, sep = ",")

t1GI     <- test1$Global_intensity
t1GImean <-  mean(t1GI , na.rm = TRUE)
t1GImin  <- min(t1GI , na.rm = T)
t1GImax  <- max(t1GI , na.rm = T)


print(paste("Arithmetic mean Global intensiy: ", GImean))
print(paste("Test one, Arithmetic mean Global intensiy: ", t1GImean))

print(paste("min Global intensiy: ", GImin))
print(paste("Test one, min Global intensiy: ", t1GImin))

print(paste("Test one, max Global intensiy: ", t1GImax))
print(paste("max Global intensiy: ", GImax))



num <- sum(t1GI < GImin || t1GI > GImax)
if (is.na(num))
    num = 0

print(paste("number of suspeccted abnormallies of Global intensiy: ", num))


## TODO find x/y of the Out of Range points
#_________________
for (i in  1:length(t1GI)) {
    #IDK how to get this working.... :'(
    #for (i in  (t1GI)) {
    if (!is.na(t1GI[i]))
        if (i < GImin) {
            print("abnormally in index", i)
            print("which has Global intensiy: ", t1GI[i])
        }
}
#----------------

#### thats would conlude Phase 2 A1 I & II-step 1&2


#----------------
##### TODO Phase 2 A1 II-step 3 .... here is what i suggest

# usage: arry[i] = rollingAvg(GI[i]..GI[n])
## NOTE: not the intuitive way which is the above being set to arr[n]... which would imply the first n-1 being 0
### sample: print(head(roll_mean(GI, n = 7L, na.rm = TRUE),40))

#GIroll <- roll_mean(GI, n = 7L, na.rm = TRUE)
# ^^ this line is entered above

for (i in  1:length(GI)) {
    if (!is.na(GI[i])) {
        if (GI[i] < (GIroll[i] * .9)) {
            print("i is too low", i)
        }
        if (GI[i] > (GIroll[i] * 1.1)) {
            print("i is too high", i)
        }
    }
}

#---------------
