package platform.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import platform.persistance.CodeRepo;
import platform.model.Code;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeHelper extends Code{
    private final CodeRepo codeRepo;
//    private static CodeHelper instance = null;
//    private static List<Code> codeList = new ArrayList<>();

    @Autowired
    public CodeHelper(CodeRepo codeRepo) {
        this.codeRepo = codeRepo;
    }
//    public static CodeHelper getInstance() {
//        if(instance == null) {
//            instance = new CodeHelper();
//        }
//        return instance;
//    }
    public void addNewCode(String code) {
        codeRepo.save(createCode(code));
    }
    public String addNewCode(String code, int viewCount, long viewTime) {
        Code newCode = createCode(code, viewCount, viewTime);
        // add to database
        codeRepo.save(newCode);
//        codeList.add(newCode);
        return newCode.getId();
    }

    public void deleteCode(String codeId) {
        codeRepo.deleteById(codeId);
    }

    public List<Code> getCodeList() {
//        return codeList;
        return (List<Code>) codeRepo.findAll();

    }
    public List<Code> getLatestCodeList() {

        int lastId = getLastCount();
        List<Code> resLatestCode = new ArrayList<>();
        List<Code> allCodes = getCodeList();
        int codeAdded = 0, codeRequired = 0;
        while (codeAdded < lastId && codeRequired < 10){

            Code temp = allCodes.get(lastId - codeAdded - 1);
            if(!temp.isSecretCode()) {
                codeRequired++;
                resLatestCode.add(temp);
            }
            codeAdded++;
        }
        return resLatestCode;
    }
    public int getLastCount() {
//        return codeList.size();
        return (int) codeRepo.count();
    }
    public boolean handleSecrectCode(Code code) {
        long timeleft = 0;
        int counter = 0;
        if(code.isTimeSet())
            timeleft = code.getTime() - ChronoUnit.SECONDS.between(code.getInTimeFormat(), LocalDateTime.now());
        if(code.isCountSet())
            counter = code.getViews() - 1;
        code.setViews(counter);
        code.setTime(timeleft);
        if (counter < 0 || timeleft < 0) {
            return true;
        }
        codeRepo.save(code);
        return false;
    }
    public Code getCodeById(String id) {
//        return codeList.get(id - 1);
        Optional<Code> idthCode = codeRepo.findById(id);
        if (idthCode.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return idthCode.get();
        }
    }

}
