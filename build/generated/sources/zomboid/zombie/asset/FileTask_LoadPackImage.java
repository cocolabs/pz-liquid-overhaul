package zombie.asset;

import java.io.InputStream;
import zombie.core.textures.ImageData;
import zombie.core.textures.TextureIDAssetManager;
import zombie.fileSystem.DeviceList;
import zombie.fileSystem.FileSystem;
import zombie.fileSystem.FileTask;
import zombie.fileSystem.IFileTaskCallback;

public final class FileTask_LoadPackImage extends FileTask {
   String m_pack_name;
   String m_image_name;
   boolean bMask;
   int m_flags;

   public FileTask_LoadPackImage(String var1, String var2, FileSystem var3, IFileTaskCallback var4) {
      super(var3, var4);
      this.m_pack_name = var1;
      this.m_image_name = var2;
      this.bMask = var3.getTexturePackAlpha(var1, var2);
      this.m_flags = var3.getTexturePackFlags(var1);
   }

   public void done() {
   }

   public Object call() throws Exception {
      TextureIDAssetManager.instance.waitFileTask();
      DeviceList var1 = this.m_file_system.getTexturePackDevice(this.m_pack_name);
      InputStream var2 = this.m_file_system.openStream(var1, this.m_image_name);
      Throwable var3 = null;

      ImageData var5;
      try {
         ImageData var4 = new ImageData(var2, this.bMask);
         if ((this.m_flags & 64) != 0) {
            var4.initMipMaps();
         }

         var5 = var4;
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (var2 != null) {
            if (var3 != null) {
               try {
                  var2.close();
               } catch (Throwable var13) {
                  var3.addSuppressed(var13);
               }
            } else {
               var2.close();
            }
         }

      }

      return var5;
   }
}
