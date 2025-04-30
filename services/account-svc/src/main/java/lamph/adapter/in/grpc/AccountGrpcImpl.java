package lamph.adapter.in.grpc;

import account.*;
import account.AccountGrpc.AccountImplBase;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lamph.emon.auth.usecase.params.BlockAccountInput;
import lamph.emon.auth.usecase.params.CreateAccountInput;
import lamph.emon.auth.usecase.params.UnBlockAccountInput;
import lamph.services.AccountService;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AccountGrpcImpl extends AccountImplBase {

    private final AccountService accountService;

    @Override
    public void createAccount(CreateAccountMessage request, StreamObserver<CreateAccountResult> responseObserver) {
        try {
            CreateAccountInput input = new CreateAccountInput(request.getUsername(), request.getPassword(), null, null);
            String savedId = accountService.createAccount(input);
            responseObserver.onNext(
                    CreateAccountResult.newBuilder().setSavedId(savedId).build()
            );
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void block(ChangeAccountStatusMessage request, StreamObserver<ChangeAccountStatusResult> responseObserver) {
        try {
            BlockAccountInput input = new BlockAccountInput(
                    request.getAccountId(), request.getReason()
            );
            accountService.blockAccount(input);

            responseObserver.onNext(
                    ChangeAccountStatusResult.newBuilder().build()
            );
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void unblock(ChangeAccountStatusMessage request, StreamObserver<ChangeAccountStatusResult> responseObserver) {
        try {
            UnBlockAccountInput input = new UnBlockAccountInput(
                    request.getAccountId(), request.getReason()
            );
            accountService.unBlockAccount(input);

            responseObserver.onNext(
                    ChangeAccountStatusResult.newBuilder().build()
            );
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
