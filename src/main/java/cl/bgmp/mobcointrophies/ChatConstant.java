package cl.bgmp.mobcointrophies;

public enum ChatConstant {
  NO_PERMISSION("You do not have permission."),
  NUMBER_STRING_EXCEPTION("Expected a number, string received instead."),
  UNKNOWN_ERROR("An unknown error has occurred."),
  INVALID_PLAYER("No players matched query."),
  INVALID_TROPHY("No trophy tiers matched query."),
  TROPHY_RECEIVED("You have received a {0} Mob Trophy!"),
  MUST_BE_ON_GROUND("You must place your trophies directly on the ground."),
  CANT_USE_TROPHY("You are not allowed to use this trophy."),
  COINS_REDEEMED("Successfully redeemed {0} Mob Coins!"),
  ZERO_COINS("You have no coins to redeem."),
  MUST_BE_ON_ISLAND("You may only place trophies on your Island!"),
  CORRUPTED_LOCATION(
      "An error has occurred while parsing one or more locations from the registrar.");

  private String message;

  ChatConstant(String message) {
    this.message = message;
  }

  public String formatAsSuccess() {
    return Utils.colourify("&a" + message);
  }

  public String formatAsException() {
    return Utils.colourify("&e⚠&c " + message);
  }
}
