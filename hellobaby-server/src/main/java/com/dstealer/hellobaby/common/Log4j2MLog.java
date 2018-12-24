package com.dstealer.hellobaby.common;

import com.mchange.v2.log.LogUtils;
import com.mchange.v2.log.MLevel;
import com.mchange.v2.log.MLog;
import com.mchange.v2.log.MLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * c3p0的log4j2实现
 * Created by LiShiwu on 02/20/2017.
 */
public class Log4j2MLog extends MLog {
    final static String CHECK_CLASS = "org.apache.logging.log4j.Logger";

    public Log4j2MLog() throws ClassNotFoundException {
        Class.forName(CHECK_CLASS);
    }

    private static String formatMessage(String rbname, String msg, Object[] params) {
        if (msg == null) {
            if (params == null)
                return "";
            else
                return LogUtils.createParamsList(params);
        } else {
            ResourceBundle rb = ResourceBundle.getBundle(rbname);
            if (rb != null) {
                String check = rb.getString(msg);
                if (check != null)
                    msg = check;
            }
            return (params == null ? msg : MessageFormat.format(msg, params));
        }
    }

    @Override
    public MLogger getMLogger(String name) {
        Logger logger = LogManager.getLogger(name);
        return new Log4j2MLogger(logger);
    }

    @Override
    public MLogger getMLogger(Class cl) {
        Logger logger = LogManager.getLogger(cl);
        return new Log4j2MLogger(logger);
    }

    @Override
    public MLogger getMLogger() {
        Logger logger = LogManager.getRootLogger();
        return new Log4j2MLogger(logger);
    }

    private final static class Log4j2MLogger implements MLogger {

        MLevel myLevel = null;

        volatile Logger logger;

        Log4j2MLogger(Logger logger) {
            this.logger = logger;
        }

        private static MLevel guessMLevel(Level lvl) {
            if (lvl == null)
                return null;
            else if (lvl == Level.ALL)
                return MLevel.ALL;
            else if (lvl == Level.DEBUG)
                return MLevel.FINEST;
            else if (lvl == Level.ERROR)
                return MLevel.SEVERE;
            else if (lvl == Level.FATAL)
                return MLevel.SEVERE;
            else if (lvl == Level.INFO)
                return MLevel.INFO;
            else if (lvl == Level.OFF)
                return MLevel.OFF;
            else if (lvl == Level.WARN)
                return MLevel.WARNING;
            else
                throw new IllegalArgumentException("Unknown level: " + lvl);
        }

        private static Level level(MLevel lvl) {
            if (lvl == null)
                return null;
            else if (lvl == MLevel.ALL)
                return Level.ALL;
            else if (lvl == MLevel.CONFIG)
                return Level.DEBUG;
            else if (lvl == MLevel.FINE)
                return Level.DEBUG;
            else if (lvl == MLevel.FINER)
                return Level.DEBUG;
            else if (lvl == MLevel.FINEST)
                return Level.DEBUG;
            else if (lvl == MLevel.INFO)
                return Level.INFO;
            else if (lvl == MLevel.INFO)
                return Level.OFF;
            else if (lvl == MLevel.SEVERE)
                return Level.ERROR;
            else if (lvl == MLevel.WARNING)
                return Level.WARN;
            else
                throw new IllegalArgumentException("Unknown MLevel: " + lvl);
        }

        private static String createMessage(String srcClass, String srcMeth, String msg) {
            StringBuffer sb = new StringBuffer(511);
            sb.append("[class: ");
            sb.append(srcClass);
            sb.append("; method: ");
            sb.append(srcMeth);
            if (!srcMeth.endsWith(")"))
                sb.append("()");
            sb.append("] ");
            sb.append(msg);
            return sb.toString();
        }

        public ResourceBundle getResourceBundle() {
            return null;
        }

        public String getResourceBundleName() {
            return null;
        }

        public Object getFilter() {
            return null;
        }

        public void setFilter(Object java14Filter) throws SecurityException {
            warning("setFilter() not supported by MLogger " + this.getClass().getName());
        }

        private void log(Level lvl, Object msg, Throwable t) {
            logger.log(lvl, msg, t);
        }

        public void log(MLevel l, String msg) {
            log(level(l), msg, null);
        }

        public void log(MLevel l, String msg, Object param) {
            log(level(l), (msg != null ? MessageFormat.format(msg, new Object[]{param}) : null), null);
        }

        public void log(MLevel l, String msg, Object[] params) {
            log(level(l), (msg != null ? MessageFormat.format(msg, params) : null), null);
        }

        public void log(MLevel l, String msg, Throwable t) {
            log(level(l), msg, t);
        }

        public void logp(MLevel l, String srcClass, String srcMeth, String msg) {
            log(level(l), createMessage(srcClass, srcMeth, msg), null);
        }

        public void logp(MLevel l, String srcClass, String srcMeth, String msg, Object param) {
            log(level(l), createMessage(srcClass, srcMeth, (msg != null ? MessageFormat.format(msg, new Object[]{param}) : null)), null);
        }

        public void logp(MLevel l, String srcClass, String srcMeth, String msg, Object[] params) {
            log(level(l), createMessage(srcClass, srcMeth, (msg != null ? MessageFormat.format(msg, params) : null)), null);
        }

        public void logp(MLevel l, String srcClass, String srcMeth, String msg, Throwable t) {
            log(level(l), createMessage(srcClass, srcMeth, msg), t);
        }

        public void logrb(MLevel l, String srcClass, String srcMeth, String rb, String msg) {
            log(level(l), createMessage(srcClass, srcMeth, formatMessage(rb, msg, null)), null);
        }

        public void logrb(MLevel l, String srcClass, String srcMeth, String rb, String msg, Object param) {
            log(level(l), createMessage(srcClass, srcMeth, formatMessage(rb, msg, new Object[]{param})), null);
        }

        public void logrb(MLevel l, String srcClass, String srcMeth, String rb, String msg, Object[] params) {
            log(level(l), createMessage(srcClass, srcMeth, formatMessage(rb, msg, params)), null);
        }

        public void logrb(MLevel l, String srcClass, String srcMeth, String rb, String msg, Throwable t) {
            log(level(l), createMessage(srcClass, srcMeth, formatMessage(rb, msg, null)), t);
        }

        public void entering(String srcClass, String srcMeth) {
            log(Level.DEBUG, createMessage(srcClass, srcMeth, "entering method."), null);
        }

        public void entering(String srcClass, String srcMeth, Object param) {
            log(Level.DEBUG, createMessage(srcClass, srcMeth, "entering method... param: " + param.toString()), null);
        }

        public void entering(String srcClass, String srcMeth, Object params[]) {
            log(Level.DEBUG, createMessage(srcClass, srcMeth, "entering method... " + LogUtils.createParamsList(params)), null);
        }

        public void exiting(String srcClass, String srcMeth) {
            log(Level.DEBUG, createMessage(srcClass, srcMeth, "exiting method."), null);
        }

        public void exiting(String srcClass, String srcMeth, Object result) {
            log(Level.DEBUG, createMessage(srcClass, srcMeth, "exiting method... result: " + result.toString()), null);
        }

        public void throwing(String srcClass, String srcMeth, Throwable t) {
            log(Level.DEBUG, createMessage(srcClass, srcMeth, "throwing exception... "), t);
        }

        public void severe(String msg) {
            log(Level.ERROR, msg, null);
        }

        public void warning(String msg) {
            log(Level.WARN, msg, null);
        }

        public void info(String msg) {
            log(Level.INFO, msg, null);
        }

        public void config(String msg) {
            log(Level.DEBUG, msg, null);
        }

        public void fine(String msg) {
            log(Level.DEBUG, msg, null);
        }

        public void finer(String msg) {
            log(Level.DEBUG, msg, null);
        }

        public void finest(String msg) {
            log(Level.DEBUG, msg, null);
        }

        public synchronized MLevel getLevel() {
            if (myLevel == null)
                myLevel = guessMLevel(logger.getLevel());
            return myLevel;
        }

        public synchronized void setLevel(MLevel l) throws SecurityException {
            myLevel = l;
        }

        public boolean isLoggable(MLevel l) {
            return logger.getLevel().equals(level(l));
        }

        public String getName() {
            return logger.getName();
        }

        public void addHandler(Object h) throws SecurityException {
            throw new UnsupportedOperationException("The 'handler' " + h + " is not compatible with MLogger " + this);
        }

        public void removeHandler(Object h) throws SecurityException {
            throw new UnsupportedOperationException("The 'handler' " + h + " is not compatible with MLogger " + this);
        }

        public Object[] getHandlers() {
            return new LinkedList().toArray();
        }

        public boolean getUseParentHandlers() {
            return false;
        }

        public void setUseParentHandlers(boolean uph) {
            throw new UnsupportedOperationException();
        }
    }
}
