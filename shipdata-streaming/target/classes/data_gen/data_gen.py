# coding=UTF-8
import random
import datetime
from threading import Timer
import time

import data


def sample_ship_number():
    return random.sample(data.ship_number, 1)[0]


def sample_monitor_round():
    return random.sample(data.monitor_round, 1)[0]


def sample_is_GPS():
    return random.sample(data.is_GPS, 1)[0]


def sample_dimension_flag():
    return random.sample(data.dimension_flag, 1)[0]


def sample_dimension():
    return random.sample(data.dimension, 1)[0]


def sample_longitude():
    return random.sample(data.longitude, 1)[0]


def sample_ship_speed():
    return random.sample(data.ship_speed, 1)[0]


def sample_ship_direction():
    return random.sample(data.ship_direction, 1)[0]


def sample_is_wind_data_valid():
    return random.sample(data.is_wind_data_valid, 1)[0]


def sample_real_wind_speed():
    return random.sample(data.real_wind_speed, 1)[0]


def sample_real_wind_direction():
    return random.sample(data.real_wind_direction, 1)[0]


def sample_wind_direction():
    return random.sample(data.wind_direction, 1)[0]


def sample_vmg():
    return random.sample(data.vmg, 1)[0]


def process_date_time():
    return [(datetime.datetime.now() + datetime.timedelta(seconds=3 * i)).strftime("%H:%M:%S") for i in range(100)]


def data_gen(count=100):
    index = 0
    ship_number = sample_ship_number()

    f = open("/Users/hecun/JavaProjects/work/fudan/code/monitor_data/" + str(data.date) + str(ship_number) + "-" + str(
        sample_monitor_round()) + "-" + str(time.time())[0: 10] + ".txt", "w+")

    f.write("Boat:" + str(ship_number) + "\n")
    f.write("Data Valid." + "\n")

    while count >= 1:
        monitor_data = '${date},{date_time},{is_GPS},{dimension_flag},{dimension},{longitude_flag},{longitude},{ship_speed},{ship_direction},{is_wind_data_valid},{real_wind_speed},{real_wind_direction},{wind_direction},{sidewards_inclination},{forward_dip_angle},{vmg}*'.format(
            date=data.date,
            date_time=process_date_time()[index],
            is_GPS=sample_is_GPS(),
            dimension_flag=sample_dimension_flag(),
            dimension=sample_dimension(),
            longitude_flag=data.longitude_flag,
            longitude=sample_longitude(),
            ship_speed=sample_ship_speed(),
            ship_direction=sample_ship_direction(),
            is_wind_data_valid=sample_is_wind_data_valid(),
            real_wind_speed=sample_real_wind_speed(),
            real_wind_direction=sample_real_wind_direction(),
            wind_direction=sample_wind_direction(),
            sidewards_inclination=data.sidewards_inclination,
            forward_dip_angle=data.forward_dip_angle,
            vmg=sample_vmg(),
        )

        f.write(monitor_data + "\n")

        count = count - 1
        index = index + 1


def process():
    """
    定时任务, 每隔3秒执行一次, 产生一个数据文件
    :return:
    """
    data_gen()
    t = Timer(3, process)
    t.start()


if __name__ == '__main__':
    process()
