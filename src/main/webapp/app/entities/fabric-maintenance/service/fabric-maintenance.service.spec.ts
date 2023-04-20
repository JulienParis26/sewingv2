import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFabricMaintenance } from '../fabric-maintenance.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fabric-maintenance.test-samples';

import { FabricMaintenanceService } from './fabric-maintenance.service';

const requireRestSample: IFabricMaintenance = {
  ...sampleWithRequiredData,
};

describe('FabricMaintenance Service', () => {
  let service: FabricMaintenanceService;
  let httpMock: HttpTestingController;
  let expectedResult: IFabricMaintenance | IFabricMaintenance[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FabricMaintenanceService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FabricMaintenance', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fabricMaintenance = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fabricMaintenance).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FabricMaintenance', () => {
      const fabricMaintenance = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fabricMaintenance).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FabricMaintenance', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FabricMaintenance', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FabricMaintenance', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFabricMaintenanceToCollectionIfMissing', () => {
      it('should add a FabricMaintenance to an empty array', () => {
        const fabricMaintenance: IFabricMaintenance = sampleWithRequiredData;
        expectedResult = service.addFabricMaintenanceToCollectionIfMissing([], fabricMaintenance);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricMaintenance);
      });

      it('should not add a FabricMaintenance to an array that contains it', () => {
        const fabricMaintenance: IFabricMaintenance = sampleWithRequiredData;
        const fabricMaintenanceCollection: IFabricMaintenance[] = [
          {
            ...fabricMaintenance,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFabricMaintenanceToCollectionIfMissing(fabricMaintenanceCollection, fabricMaintenance);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FabricMaintenance to an array that doesn't contain it", () => {
        const fabricMaintenance: IFabricMaintenance = sampleWithRequiredData;
        const fabricMaintenanceCollection: IFabricMaintenance[] = [sampleWithPartialData];
        expectedResult = service.addFabricMaintenanceToCollectionIfMissing(fabricMaintenanceCollection, fabricMaintenance);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricMaintenance);
      });

      it('should add only unique FabricMaintenance to an array', () => {
        const fabricMaintenanceArray: IFabricMaintenance[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fabricMaintenanceCollection: IFabricMaintenance[] = [sampleWithRequiredData];
        expectedResult = service.addFabricMaintenanceToCollectionIfMissing(fabricMaintenanceCollection, ...fabricMaintenanceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fabricMaintenance: IFabricMaintenance = sampleWithRequiredData;
        const fabricMaintenance2: IFabricMaintenance = sampleWithPartialData;
        expectedResult = service.addFabricMaintenanceToCollectionIfMissing([], fabricMaintenance, fabricMaintenance2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricMaintenance);
        expect(expectedResult).toContain(fabricMaintenance2);
      });

      it('should accept null and undefined values', () => {
        const fabricMaintenance: IFabricMaintenance = sampleWithRequiredData;
        expectedResult = service.addFabricMaintenanceToCollectionIfMissing([], null, fabricMaintenance, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricMaintenance);
      });

      it('should return initial array if no FabricMaintenance is added', () => {
        const fabricMaintenanceCollection: IFabricMaintenance[] = [sampleWithRequiredData];
        expectedResult = service.addFabricMaintenanceToCollectionIfMissing(fabricMaintenanceCollection, undefined, null);
        expect(expectedResult).toEqual(fabricMaintenanceCollection);
      });
    });

    describe('compareFabricMaintenance', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFabricMaintenance(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFabricMaintenance(entity1, entity2);
        const compareResult2 = service.compareFabricMaintenance(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFabricMaintenance(entity1, entity2);
        const compareResult2 = service.compareFabricMaintenance(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFabricMaintenance(entity1, entity2);
        const compareResult2 = service.compareFabricMaintenance(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
