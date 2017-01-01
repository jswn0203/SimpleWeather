package com.zjy.simpleweather.utils;

import android.util.Log;

import com.zjy.simpleweather.entiy.MyContast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by 极速蜗牛 on 2016/12/26 0026.
 */

public class Obj2File {


    /**
     * 写入对象，将对象保存；
     */
    public static void WriteObj2File(Object object, String fileObjName) {
        FileOutputStream out = null;
        ObjectOutputStream objout = null;
        File file = new File(fileObjName);
        try {
            Log.i(MyContast.TAG, "try************");
            out = new FileOutputStream(file);
            objout = new ObjectOutputStream(out);
            objout.writeObject(object);
            objout.flush();
        } catch (FileNotFoundException e) {
            Log.i(MyContast.TAG, "FileNotFoundException************");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i(MyContast.TAG, "IOException************");
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objout != null) {
                try {
                    objout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object readFile2Obj(String fileObjName) {
        FileInputStream in = null;
        ObjectInputStream inObj = null;
        File file = new File(fileObjName);
        try {
            Log.i(MyContast.TAG, "try************");
            in = new FileInputStream(file);
            inObj = new ObjectInputStream(in);
            Object object = inObj.readObject();
            Log.i(MyContast.TAG, "success************" + object.toString());
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(MyContast.TAG, "FileNotFoundException************");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(MyContast.TAG, "IOException************");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.i(MyContast.TAG, "ClassNotFoundException************");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inObj != null) {
                try {
                    inObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i(MyContast.TAG, "null************");
        return null;
    }

    /**
     * 将字节转换为对象
     *
     * @param bytes
     * @return
     */
    public static Object Bytes2Obj(byte[] bytes) {
        Object obj;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oi != null) {
                    oi.close();
                }
                if (bi != null) {
                    bi.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将对象转换为字节数组
     *
     * @param obj
     * @return
     */
    public static byte[] Obj2Bytes(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream out = null;
        try {
            bo = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bo);
            out.writeObject(obj);
            out.flush();
            bytes = bo.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断是否写入缓存
     *
     * @param bytes
     * @param out_w
     * @return
     */
    public static boolean saveio(byte[] bytes, OutputStream out_w) {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(out_w, 8 * 1024);
            if (bytes.length > 0) {
                out.write(bytes);
                out.flush();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
