package br.com.guilhermetupi.ecommerce.common.exception;

public enum IntegrationService {

    ACCOUNT("Promo Account", IntegrationServiceType.INTERNAL),
    CAMPAIGN("Promo Campaign", IntegrationServiceType.INTERNAL),
    CAMPAIGN_ENGINE_DRAW("Promo Campaign Engine Draw", IntegrationServiceType.INTERNAL),
    CAMPAIGN_ENGINE_CASHBACK("Promo Campaign Engine Cashback", IntegrationServiceType.INTERNAL),
    CAMPAIGN_ENGINE_CULTURAL_CONTEST("Promo Campaign Engine Cultural Contest", IntegrationServiceType.INTERNAL),
    CAMPAIGN_ENGINE_COLLECT_TO_WIN("Promo Campaign Engine Cultural Contest", IntegrationServiceType.INTERNAL),
    CAMPAIGN_ENGINE_INSTANT_PRIZE("Promo Campaign Engine Instant Prize", IntegrationServiceType.INTERNAL),
    COMMUNICATION("Promo Communication", IntegrationServiceType.INTERNAL),
    TRANSFER("Promo Transfer", IntegrationServiceType.INTERNAL),
    SEND_PULSE("Send Pulse", IntegrationServiceType.EXTERNAL),
    PIC_PAY("PicPay", IntegrationServiceType.EXTERNAL),
    UNKNOWN("Desconhecido", IntegrationServiceType.UNKNOWN);

    private final String name;

    private final IntegrationServiceType type;

    IntegrationService(String name, IntegrationServiceType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public IntegrationServiceType getType() {
        return type;
    }
}
