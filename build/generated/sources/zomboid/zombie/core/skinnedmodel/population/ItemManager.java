package zombie.core.skinnedmodel.population;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import zombie.core.Rand;

@XmlRootElement
public class ItemManager {
   public ArrayList m_Items = new ArrayList();
   @XmlTransient
   public static ItemManager instance;

   public static void init() {
      instance = Parse("media/items/items.xml");
   }

   public CarriedItem GetRandomItem() {
      int var1 = Rand.Next(this.m_Items.size() + 1);
      return var1 < this.m_Items.size() ? (CarriedItem)this.m_Items.get(var1) : null;
   }

   public static ItemManager Parse(String var0) {
      try {
         return parse(var0);
      } catch (JAXBException var2) {
         var2.printStackTrace();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      return null;
   }

   public static ItemManager parse(String var0) throws JAXBException, IOException {
      FileInputStream var1 = new FileInputStream(var0);
      Throwable var2 = null;

      ItemManager var6;
      try {
         JAXBContext var3 = JAXBContext.newInstance(ItemManager.class);
         Unmarshaller var4 = var3.createUnmarshaller();
         ItemManager var5 = (ItemManager)var4.unmarshal(var1);
         var6 = var5;
      } catch (Throwable var15) {
         var2 = var15;
         throw var15;
      } finally {
         if (var1 != null) {
            if (var2 != null) {
               try {
                  var1.close();
               } catch (Throwable var14) {
                  var2.addSuppressed(var14);
               }
            } else {
               var1.close();
            }
         }

      }

      return var6;
   }

   public static void Write(ItemManager var0, String var1) {
      try {
         write(var0, var1);
      } catch (JAXBException var3) {
         var3.printStackTrace();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static void write(ItemManager var0, String var1) throws IOException, JAXBException {
      FileOutputStream var2 = new FileOutputStream(var1);
      Throwable var3 = null;

      try {
         JAXBContext var4 = JAXBContext.newInstance(ItemManager.class);
         Marshaller var5 = var4.createMarshaller();
         var5.setProperty("jaxb.formatted.output", Boolean.TRUE);
         var5.marshal(var0, var2);
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (var2 != null) {
            if (var3 != null) {
               try {
                  var2.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               var2.close();
            }
         }

      }

   }
}
