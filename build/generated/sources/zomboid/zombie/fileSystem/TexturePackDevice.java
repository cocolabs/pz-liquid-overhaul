package zombie.fileSystem;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import zombie.ZomboidFileSystem;
import zombie.core.textures.TexturePackPage;

public final class TexturePackDevice implements IFileDevice {
   String m_name;
   String m_filename;
   final ArrayList m_pages = new ArrayList();
   final HashMap m_pagemap = new HashMap();
   final HashMap m_submap = new HashMap();
   int m_textureFlags;

   public TexturePackDevice(String var1, int var2) {
      this.m_name = var1;
      this.m_filename = ZomboidFileSystem.instance.getString("media/texturepacks/" + var1 + ".pack");
      this.m_textureFlags = var2;
   }

   public IFile createFile(IFile var1) {
      return null;
   }

   public void destroyFile(IFile var1) {
   }

   public InputStream createStream(String var1, InputStream var2) throws IOException {
      this.initMetaData();
      return new TexturePackDevice.TexturePackInputStream(var1, this);
   }

   public void destroyStream(InputStream var1) {
      if (var1 instanceof TexturePackDevice.TexturePackInputStream) {
      }

   }

   public String name() {
      return this.m_name;
   }

   public void getSubTextureInfo(FileSystem.TexturePackTextures var1) throws IOException {
      this.initMetaData();
      Iterator var2 = this.m_submap.values().iterator();

      while(var2.hasNext()) {
         TexturePackDevice.SubTexture var3 = (TexturePackDevice.SubTexture)var2.next();
         FileSystem.SubTexture var4 = new FileSystem.SubTexture(this.name(), var3.m_page.m_name, var3.m_info);
         var1.put(var3.m_info.name, var4);
      }

   }

   private void initMetaData() throws IOException {
      if (this.m_pages.isEmpty()) {
         FileInputStream var1 = new FileInputStream(this.m_filename);
         Throwable var2 = null;

         try {
            BufferedInputStream var3 = new BufferedInputStream(var1);
            Throwable var4 = null;

            try {
               TexturePackDevice.PositionInputStream var5 = new TexturePackDevice.PositionInputStream(var3);
               Throwable var6 = null;

               try {
                  int var7 = TexturePackPage.readInt((InputStream)var5);

                  for(int var8 = 0; var8 < var7; ++var8) {
                     TexturePackDevice.Page var9 = this.readPage(var5);
                     this.m_pages.add(var9);
                     this.m_pagemap.put(var9.m_name, var9);
                     Iterator var10 = var9.m_sub.iterator();

                     while(var10.hasNext()) {
                        TexturePackPage.SubTextureInfo var11 = (TexturePackPage.SubTextureInfo)var10.next();
                        this.m_submap.put(var11.name, new TexturePackDevice.SubTexture(var9, var11));
                     }
                  }
               } catch (Throwable var54) {
                  var6 = var54;
                  throw var54;
               } finally {
                  if (var5 != null) {
                     if (var6 != null) {
                        try {
                           var5.close();
                        } catch (Throwable var53) {
                           var6.addSuppressed(var53);
                        }
                     } else {
                        var5.close();
                     }
                  }

               }
            } catch (Throwable var56) {
               var4 = var56;
               throw var56;
            } finally {
               if (var3 != null) {
                  if (var4 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var52) {
                        var4.addSuppressed(var52);
                     }
                  } else {
                     var3.close();
                  }
               }

            }
         } catch (Throwable var58) {
            var2 = var58;
            throw var58;
         } finally {
            if (var1 != null) {
               if (var2 != null) {
                  try {
                     var1.close();
                  } catch (Throwable var51) {
                     var2.addSuppressed(var51);
                  }
               } else {
                  var1.close();
               }
            }

         }

      }
   }

   private TexturePackDevice.Page readPage(TexturePackDevice.PositionInputStream var1) throws IOException {
      TexturePackDevice.Page var2 = new TexturePackDevice.Page();
      String var3 = TexturePackPage.ReadString(var1);
      int var4 = TexturePackPage.readInt((InputStream)var1);
      boolean var5 = TexturePackPage.readInt((InputStream)var1) != 0;
      var2.m_name = var3;
      var2.m_has_alpha = var5;

      int var6;
      for(var6 = 0; var6 < var4; ++var6) {
         String var7 = TexturePackPage.ReadString(var1);
         int var8 = TexturePackPage.readInt((InputStream)var1);
         int var9 = TexturePackPage.readInt((InputStream)var1);
         int var10 = TexturePackPage.readInt((InputStream)var1);
         int var11 = TexturePackPage.readInt((InputStream)var1);
         int var12 = TexturePackPage.readInt((InputStream)var1);
         int var13 = TexturePackPage.readInt((InputStream)var1);
         int var14 = TexturePackPage.readInt((InputStream)var1);
         int var15 = TexturePackPage.readInt((InputStream)var1);
         var2.m_sub.add(new TexturePackPage.SubTextureInfo(var8, var9, var10, var11, var12, var13, var14, var15, var7));
      }

      var2.m_png_start = var1.getPosition();
      boolean var16 = false;

      do {
         var6 = TexturePackPage.readIntByte(var1);
      } while(var6 != -559038737);

      return var2;
   }

   public boolean isAlpha(String var1) {
      TexturePackDevice.Page var2 = (TexturePackDevice.Page)this.m_pagemap.get(var1);
      return var2.m_has_alpha;
   }

   public int getTextureFlags() {
      return this.m_textureFlags;
   }

   public final class PositionInputStream extends FilterInputStream {
      private long pos = 0L;
      private long mark = 0L;

      public PositionInputStream(InputStream var2) {
         super(var2);
      }

      public synchronized long getPosition() {
         return this.pos;
      }

      public synchronized int read() throws IOException {
         int var1 = super.read();
         if (var1 >= 0) {
            ++this.pos;
         }

         return var1;
      }

      public synchronized int read(byte[] var1, int var2, int var3) throws IOException {
         int var4 = super.read(var1, var2, var3);
         if (var4 > 0) {
            this.pos += (long)var4;
         }

         return var4;
      }

      public synchronized long skip(long var1) throws IOException {
         long var3 = super.skip(var1);
         if (var3 > 0L) {
            this.pos += var3;
         }

         return var3;
      }

      public synchronized void mark(int var1) {
         super.mark(var1);
         this.mark = this.pos;
      }

      public synchronized void reset() throws IOException {
         if (!this.markSupported()) {
            throw new IOException("Mark not supported.");
         } else {
            super.reset();
            this.pos = this.mark;
         }
      }
   }

   static class TexturePackInputStream extends FileInputStream {
      TexturePackDevice m_device;

      TexturePackInputStream(String var1, TexturePackDevice var2) throws IOException {
         super(var2.m_filename);
         this.m_device = var2;
         TexturePackDevice.Page var3 = (TexturePackDevice.Page)this.m_device.m_pagemap.get(var1);
         if (var3 == null) {
            throw new FileNotFoundException();
         } else {
            this.skip(var3.m_png_start);
         }
      }
   }

   static final class SubTexture {
      final TexturePackDevice.Page m_page;
      final TexturePackPage.SubTextureInfo m_info;

      SubTexture(TexturePackDevice.Page var1, TexturePackPage.SubTextureInfo var2) {
         this.m_page = var1;
         this.m_info = var2;
      }
   }

   static final class Page {
      String m_name;
      boolean m_has_alpha = false;
      long m_png_start = -1L;
      final ArrayList m_sub = new ArrayList();
   }
}
