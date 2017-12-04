package me.hecun.shipdata.service.impl;

import me.hecun.shipdata.repository.MonitorDataRepository;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.DataFileException;
import me.hecun.shipdata.service.ParseDataFileService;
import me.hecun.shipdata.util.FileCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hecun
 * @date 2017/10/27
 */
@Service
public class ParseDataFileServiceImpl implements ParseDataFileService {

    @Override
    public String parseDataFile(String fileName) {
        if (FileCheckUtil.checkDataValid(fileName)) {
            String shipNumber = FileCheckUtil.getShipNumber(fileName);
            FileCheckUtil.filterDataFile(fileName, "$");
            FileCheckUtil.filterDataFile(fileName, "*");

            return shipNumber;
        } else {
            throw new DataFileException(ResponseEnum.DATA_FILE_ERROR);
        }
    }
}
