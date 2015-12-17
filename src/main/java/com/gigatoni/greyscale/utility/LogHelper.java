package com.gigatoni.greyscale.utility;

import com.gigatoni.greyscale.handler.ConfigHandler;
import com.gigatoni.greyscale.reference.Reference;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;


public class LogHelper {
    /**
     * Used for logging when debug is turned on in the config
     *
     * @param obj object to log
     */
    public static void debug(Object obj)
    {
        if (!ConfigHandler.debugMode)
        {
            log(Level.INFO, obj);
        }
        else
        {
            log(Level.DEBUG, obj);
        }
    }

    /**
     * Used for logging an exception
     *
     * @param obj       object to log
     * @param exception exception to log
     * @param level     level of the log
     */
    public static void exception(Object obj, Throwable exception, Level level)
    {
        FMLLog.log(Reference.MOD_NAME, level, exception, String.valueOf(obj));
    }

    /**
     * Used for logging an exception
     *
     * @param exception exception to log
     * @param level     level of the log
     */
    public static void exception(Throwable exception, Level level)
    {
        FMLLog.log(Reference.MOD_NAME, level, exception, exception.toString());
    }

    /**
     * Used for logging a warning
     *
     * @param obj
     */
    public static void warn(Object obj)
    {
        log(Level.WARN, obj);
    }

    /**
     * Used for logging in any case
     *
     * @param obj object to log
     */
    public static void info(Object obj)
    {
        log(Level.INFO, obj);
    }

    /**
     * General logging method
     *
     * @param level Level of the log
     * @param obj   object to log
     */
    public static void log(Level level, Object obj)
    {
        FMLLog.log(Reference.MOD_NAME, level, String.valueOf(obj));
    }
}