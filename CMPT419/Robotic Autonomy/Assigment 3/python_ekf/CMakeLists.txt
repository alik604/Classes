cmake_minimum_required(VERSION 3.0.2)
project(python_ekf)


find_package(catkin REQUIRED COMPONENTS 
    rospy
)

catkin_package(
#  INCLUDE_DIRS include
#  LIBRARIES python_ekf
#  CATKIN_DEPENDS rospy
#  DEPENDS system_lib
)
include_directories(
  ${catkin_INCLUDE_DIRS}
)

catkin_install_python(PROGRAMS
  src/ekf.py
  src/visualizer.py
  DESTINATION ${CATKIN_PACKAGE_SHARE_DESTINATION}
)

install(FILES
  launch/start_ekf.launch
  DESTINATION ${CATKIN_PACKAGE_SHARE_DESTINATION}/launch
)


