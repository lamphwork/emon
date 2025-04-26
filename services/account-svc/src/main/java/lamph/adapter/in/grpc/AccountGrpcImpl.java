package lamph.adapter.in.grpc;

import account.*;
import account.AccountGrpc.AccountImplBase;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lamph.emon.auth.usecase.params.CreateAccountInput;
import lamph.services.AccountService;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AccountGrpcImpl extends AccountImplBase {

    private final AccountService accountService;

    @Override
    public void createAccount(CreateAccountMessage request, StreamObserver<CreateAccountResult> responseObserver) {
        try {
            CreateAccountInput input = new CreateAccountInput(request.getUsername(), request.getPassword());
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
        super.block(request, responseObserver);
    }

    @Override
    public void unblock(ChangeAccountStatusMessage request, StreamObserver<ChangeAccountStatusResult> responseObserver) {
        super.unblock(request, responseObserver);
    }
}
