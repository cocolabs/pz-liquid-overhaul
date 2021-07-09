package zombie.characters;

import zombie.characterTextures.BloodBodyPartType;
import zombie.characters.BodyDamage.BodyDamage;
import zombie.inventory.types.HandWeapon;
import zombie.iso.Vector2;
import zombie.vehicles.BaseVehicle;

public interface ILuaGameCharacterDamage {
   BodyDamage getBodyDamage();

   BodyDamage getBodyDamageRemote();

   float getHealth();

   void setHealth(float var1);

   void Hit(BaseVehicle var1, float var2, float var3, Vector2 var4);

   void Hit(HandWeapon var1, IsoGameCharacter var2, float var3, boolean var4, float var5);

   void Hit(HandWeapon var1, IsoGameCharacter var2, float var3, boolean var4, float var5, boolean var6);

   boolean isOnFire();

   void StopBurning();

   void sendStopBurning();

   int getLastHitCount();

   void setLastHitCount(int var1);

   void addHole(BloodBodyPartType var1);

   void addBlood(BloodBodyPartType var1, boolean var2, boolean var3, boolean var4);

   boolean isBumped();

   String getBumpType();

   boolean isOnDeathDone();

   void setOnDeathDone(boolean var1);

   boolean isDeathDragDown();

   void setDeathDragDown(boolean var1);

   boolean isPlayingDeathSound();

   void setPlayingDeathSound(boolean var1);
}
