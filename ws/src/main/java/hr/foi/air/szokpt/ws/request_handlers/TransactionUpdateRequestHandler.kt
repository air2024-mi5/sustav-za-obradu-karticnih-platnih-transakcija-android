package hr.foi.air.szokpt.ws.request_handlers

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.TransactionData
import hr.foi.air.szokpt.ws.models.responses.Transaction
import retrofit2.Call

class TransactionUpdateRequestHandler(
    private val jwtToken: String,
    private val newTransactionData: TransactionData
) :
    TemplateRequestHandler<Unit>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Unit>> {
        val transaction = Transaction(
            guid = newTransactionData.guid,
            amount = newTransactionData.amount,
            currency = newTransactionData.currency,
            trxType = newTransactionData.trxType,
            installmentsNumber = newTransactionData.installmentsNumber,
            installmentsCreditor = newTransactionData.installmentsCreditor,
            cardBrand = newTransactionData.cardBrand,
            transactionTimestamp = newTransactionData.transactionTimestamp,
            maskedPan = newTransactionData.maskedPan,
            pinUsed = newTransactionData.pinUsed,
            responseCode = newTransactionData.responseCode,
            approvalCode = newTransactionData.approvalCode,
            processed = newTransactionData.processed
        );
        val service = NetworkService.transactionsService
        return service.updateTransaction(
            "Bearer $jwtToken",
            newTransactionData.guid,
            transaction
        )
    }
}