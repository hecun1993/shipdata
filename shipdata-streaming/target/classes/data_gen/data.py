# coding=UTF-8

import random
import time

# 产生模拟的shipNumber
ship_number = ["01", "02", "03", "04", "05", "06"]

# 产生模拟的轮次
monitor_round = ["01", "02", "03", "04", "05"]

# 产生模拟的date
date = time.strftime("%Y-%m-%d", time.localtime())

# 产生模拟的time
date_time = time.strftime("%H:%M:%S", time.localtime())

is_GPS = ["A", "N"]

dimension_flag = ["N", "A"]

dimension = [round(random.uniform(3000, 4000), 4) for n in range(10)]

longitude_flag = "E"

longitude = [round(random.uniform(12000, 13000), 4) for a in range(10)]

# 产生模拟的船速
ship_speed = [round(random.uniform(10, 20), 1) for m in range(15)]

# 产生模拟的船向
ship_direction = [round(random.uniform(1, 400), 1) for j in range(15)]

is_wind_data_valid = ["A", "N"]

# 产生模拟的风速
real_wind_speed = [round(random.uniform(0, 1), 1) for b in range(10)]

real_wind_direction = [round(random.uniform(100, 200), 1) for c in range(10)]

# 产生模拟的风向
wind_direction = [round(random.uniform(1, 400), 1) for i in range(15)]

sidewards_inclination = "---.-"

forward_dip_angle = "---.-"

vmg = ["+" + str(round(random.uniform(0, 1), 1)) for d in range(10)]
