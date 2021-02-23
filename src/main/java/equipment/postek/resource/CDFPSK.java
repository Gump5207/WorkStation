package equipment.postek.resource;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @ClassName: CDFPSK
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2020/9/1016:13
 */
public interface CDFPSK extends Library {

    CDFPSK instance = Native.load("dll/postek/CDFPSK.dll", CDFPSK.class);


}
