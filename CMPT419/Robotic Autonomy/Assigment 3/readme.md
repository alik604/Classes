Installation:
```
Docker version(tested with Nvidia GPU):
First follow this tutorial to install docker with GPU https://cnvrg.io/how-to-setup-docker-and-nvidia-docker-2-0-on-ubuntu-18-04/
Then first run "bash docker/run_script.bash"
If you want to run this docker again you can use "bash docker/exec_script.bash"
```
```
Without Docker:
First follow ROS instalation:
http://wiki.ros.org/melodic/Installation/Ubuntu
then:
sudo apt install python-catkin-tools
sudo apt install ros-melodic-turtlebot3-teleop
export catkinDIR="your catkin directory"
cd catkinDIR/src
git clone git@github.com:payamn/turtlebot3_from_scratch.git
cd turtlebot3_from_scratch
git submodule update --recursive
cd ../
catkin build
source devel/setup.bash


or 
~/catkin_ws/devel/setup.bash
```
# test
```
roslaunch nuslam slam.launch debug:=True
```


~/catkin_ws/src/turtlebot3_from_scratch
