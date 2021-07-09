package zombie.characters;

import fmod.javafmod;
import fmod.fmod.EmitterType;
import fmod.fmod.FMODManager;
import fmod.fmod.FMODSoundBank;
import fmod.fmod.FMODSoundEmitter;
import fmod.fmod.FMODVoice;
import zombie.SoundManager;
import zombie.debug.DebugLog;
import zombie.debug.DebugType;
import zombie.interfaces.ICommonSoundEmitter;
import zombie.iso.IsoObject;
import zombie.network.GameServer;

public final class CharacterSoundEmitter extends BaseCharacterSoundEmitter implements ICommonSoundEmitter {
   float currentPriority;
   final FMODSoundEmitter vocals = new FMODSoundEmitter();
   final FMODSoundEmitter footsteps = new FMODSoundEmitter();
   final FMODSoundEmitter extra = new FMODSoundEmitter();

   public CharacterSoundEmitter(IsoGameCharacter var1) {
      super(var1);
      this.vocals.emitterType = EmitterType.Voice;
      this.vocals.parent = this.character;
      this.footsteps.emitterType = EmitterType.Footstep;
      this.footsteps.parent = this.character;
      this.extra.emitterType = EmitterType.Extra;
      this.extra.parent = this.character;
   }

   public void register() {
      SoundManager.instance.registerEmitter(this.vocals);
      SoundManager.instance.registerEmitter(this.footsteps);
      SoundManager.instance.registerEmitter(this.extra);
   }

   public void unregister() {
      SoundManager.instance.unregisterEmitter(this.vocals);
      SoundManager.instance.unregisterEmitter(this.footsteps);
      SoundManager.instance.unregisterEmitter(this.extra);
   }

   public long playVocals(String var1) {
      if (GameServer.bServer) {
         return 0L;
      } else {
         FMODVoice var2 = FMODSoundBank.instance.getVoice(var1);
         if (var2 == null) {
            long var6 = this.vocals.playSoundImpl(var1, false, (IsoObject)null);
            return var6;
         } else {
            float var3 = var2.priority;
            long var4 = this.vocals.playSoundImpl(var2.sound, false, (IsoObject)null);
            this.currentPriority = var3;
            return var4;
         }
      }
   }

   CharacterSoundEmitter.footstep getFootstepToPlay() {
      if (FMODManager.instance.getNumListeners() == 1) {
         for(int var1 = 0; var1 < IsoPlayer.numPlayers; ++var1) {
            IsoPlayer var2 = IsoPlayer.players[var1];
            if (var2 != null && var2 != this.character && !var2.Traits.Deaf.isSet()) {
               if ((int)var2.getZ() < (int)this.character.getZ()) {
                  return CharacterSoundEmitter.footstep.upstairs;
               }
               break;
            }
         }
      }

      IsoObject var4 = this.character.getCurrentSquare().getFloor();
      if (var4 != null && var4.getSprite() != null && var4.getSprite().getName() != null) {
         String var5 = var4.getSprite().getName();
         if (!var5.endsWith("blends_natural_01_5") && !var5.endsWith("blends_natural_01_6") && !var5.endsWith("blends_natural_01_7") && !var5.endsWith("blends_natural_01_0")) {
            if (!var5.endsWith("blends_street_01_48") && !var5.endsWith("blends_street_01_53") && !var5.endsWith("blends_street_01_54") && !var5.endsWith("blends_street_01_55")) {
               if (var5.startsWith("blends_natural_01")) {
                  return CharacterSoundEmitter.footstep.grass;
               } else if (var5.contains("floors_interior_tilesandwood_01_")) {
                  int var3 = Integer.parseInt(var5.replaceFirst("floors_interior_tilesandwood_01_", ""));
                  return var3 > 40 && var3 < 48 ? CharacterSoundEmitter.footstep.wood : CharacterSoundEmitter.footstep.concrete;
               } else if (var5.startsWith("carpentry_02_")) {
                  return CharacterSoundEmitter.footstep.wood;
               } else {
                  return var5.contains("interior_carpet_") ? CharacterSoundEmitter.footstep.wood : CharacterSoundEmitter.footstep.concrete;
               }
            } else {
               return CharacterSoundEmitter.footstep.gravel;
            }
         } else {
            return CharacterSoundEmitter.footstep.gravel;
         }
      } else {
         return CharacterSoundEmitter.footstep.concrete;
      }
   }

   public void playFootsteps(String var1, float var2) {
      if (!GameServer.bServer) {
         long var3 = this.footsteps.playSoundImpl(var1, false, (IsoObject)null);
         if (var3 != 0L) {
            CharacterSoundEmitter.footstep var5 = this.getFootstepToPlay();
            javafmod.FMOD_Studio_SetParameter(var3, "MoveSpeed", var2);
            switch(var5) {
            case wood:
               javafmod.FMOD_Studio_SetParameter(var3, "Wood", 1.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Grass", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Upstairs", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Snow", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Concrete", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Gravel", 0.0F);
               break;
            case grass:
               javafmod.FMOD_Studio_SetParameter(var3, "Grass", 1.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Wood", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Upstairs", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Snow", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Concrete", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Gravel", 0.0F);
               break;
            case upstairs:
               javafmod.FMOD_Studio_SetParameter(var3, "Upstairs", 1.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Wood", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Grass", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Snow", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Concrete", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Gravel", 0.0F);
               break;
            case snow:
               javafmod.FMOD_Studio_SetParameter(var3, "Snow", 1.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Wood", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Grass", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Upstairs", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Concrete", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Gravel", 0.0F);
               break;
            case gravel:
               javafmod.FMOD_Studio_SetParameter(var3, "Gravel", 1.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Wood", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Snow", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Grass", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Upstairs", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Concrete", 0.0F);
               break;
            case concrete:
               javafmod.FMOD_Studio_SetParameter(var3, "Concrete", 1.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Wood", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Grass", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Upstairs", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Snow", 0.0F);
               javafmod.FMOD_Studio_SetParameter(var3, "Gravel", 0.0F);
            }

         }
      }
   }

   public long playSound(String var1) {
      if (DebugLog.isEnabled(DebugType.Sound)) {
         DebugLog.Sound.debugln("Playing sound: " + var1 + (this.character.isZombie() ? " for zombie" : " for player"));
      }

      return this.character.isInvisible() ? 0L : this.extra.playSound(var1);
   }

   public long playSound(String var1, boolean var2) {
      if (DebugLog.isEnabled(DebugType.Sound)) {
         DebugLog.Sound.debugln("Playing sound: " + var1 + (this.character.isZombie() ? " for zombie" : " for player"));
      }

      return this.extra.playSound(var1, var2);
   }

   public long playSound(String var1, IsoObject var2) {
      if (DebugLog.isEnabled(DebugType.Sound)) {
         DebugLog.Sound.debugln("Playing sound: " + var1 + (this.character.isZombie() ? " for zombie" : " for player"));
      }

      return GameServer.bServer ? 0L : this.extra.playSound(var1, var2);
   }

   public long playSoundImpl(String var1, IsoObject var2) {
      if (DebugLog.isEnabled(DebugType.Sound)) {
         DebugLog.Sound.debugln("Playing sound: " + var1 + (this.character.isZombie() ? " for zombie" : " for player"));
      }

      return this.extra.playSoundImpl(var1, false, var2);
   }

   public void tick() {
      this.vocals.tick();
      this.footsteps.tick();
      this.extra.tick();
   }

   public void setPos(float var1, float var2, float var3) {
      this.set(var1, var2, var3);
   }

   public void set(float var1, float var2, float var3) {
      this.vocals.x = this.footsteps.x = this.extra.x = var1;
      this.vocals.y = this.footsteps.y = this.extra.y = var2;
      this.vocals.z = this.footsteps.z = this.extra.z = var3;
   }

   public boolean isEmpty() {
      return this.isClear();
   }

   public boolean isClear() {
      return this.vocals.isEmpty() && this.footsteps.isEmpty() && this.extra.isEmpty();
   }

   public void setPitch(long var1, float var3) {
      this.extra.setPitch(var1, var3);
      this.footsteps.setPitch(var1, var3);
      this.vocals.setPitch(var1, var3);
   }

   public void setVolume(long var1, float var3) {
      this.extra.setVolume(var1, var3);
      this.footsteps.setVolume(var1, var3);
      this.vocals.setVolume(var1, var3);
   }

   public int stopSound(long var1) {
      this.extra.stopSound(var1);
      this.footsteps.stopSound(var1);
      this.vocals.stopSound(var1);
      return 0;
   }

   public int stopSoundByName(String var1) {
      this.extra.stopSoundByName(var1);
      this.footsteps.stopSoundByName(var1);
      this.vocals.stopSoundByName(var1);
      return 0;
   }

   public boolean hasSoundsToStart() {
      return this.extra.hasSoundsToStart() || this.footsteps.hasSoundsToStart() || this.vocals.hasSoundsToStart();
   }

   public boolean isPlaying(long var1) {
      return this.extra.isPlaying(var1) || this.footsteps.isPlaying(var1) || this.vocals.isPlaying(var1);
   }

   public boolean isPlaying(String var1) {
      return this.extra.isPlaying(var1) || this.footsteps.isPlaying(var1) || this.vocals.isPlaying(var1);
   }

   static enum footstep {
      upstairs,
      grass,
      wood,
      concrete,
      gravel,
      snow;
   }
}
