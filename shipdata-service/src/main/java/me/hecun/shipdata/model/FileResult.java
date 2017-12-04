package me.hecun.shipdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author hecun
 * @date 2017/10/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResult {

    private String shipNumber;
    private String roundId;
}
