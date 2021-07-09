package zombie.asset;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import zombie.core.textures.ImageData;
import zombie.core.textures.TextureIDAssetManager;
import zombie.debug.DebugOptions;
import zombie.fileSystem.FileSystem;
import zombie.fileSystem.FileTask;
import zombie.fileSystem.IFileTaskCallback;

public final class FileTask_LoadImageData extends FileTask {
   String m_image_name;
   boolean bMask = false;

   public FileTask_LoadImageData(String var1, FileSystem var2, IFileTaskCallback var3) {
      super(var2, var3);
      this.m_image_name = var1;
   }

   public String getErrorMessage() {
      return this.m_image_name;
   }

   public void done() {
   }

   public Object call() throws Exception {
      TextureIDAssetManager.instance.waitFileTask();
      if (DebugOptions.instance.AssetSlowLoad.getValue()) {
         try {
            Thread.sleep(500L);
         } catch (InterruptedException var31) {
         }
      }

      FileInputStream var1 = new FileInputStream(this.m_image_name);
      Throwable var2 = null;

      Object var5;
      try {
         BufferedInputStream var3 = new BufferedInputStream(var1);
         Throwable var4 = null;

         try {
            var5 = new ImageData(var3, this.bMask);
         } catch (Throwable var30) {
            var5 = var30;
            var4 = var30;
            throw var30;
         } finally {
            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var29) {
                     var4.addSuppressed(var29);
                  }
               } else {
                  var3.close();
               }
            }

         }
      } catch (Throwable var33) {
         var2 = var33;
         throw var33;
      } finally {
         if (var1 != null) {
            if (var2 != null) {
               try {
                  var1.close();
               } catch (Throwable var28) {
                  var2.addSuppressed(var28);
               }
            } else {
               var1.close();
            }
         }

      }

      return var5;
   }
}
